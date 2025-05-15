import {useContext, useState} from "react";
import {AppContext} from "../../context/AppContext.jsx";
import ReceiptPopup from "../ReceiptPopup/ReceiptPopup.jsx";
import toast from "react-hot-toast";
import {createOrder, deleteOrder} from "../../service/OrderService.js";
import {createRazorpayOrder, verifyRazorpayPayment} from "../../service/PaymentService.js";
import {AppConstants} from "../../Util/constants.js";

export default function CartSummary({customerName, setCustomerName, mobileNumber, setMobileNumber}) {

    const {cartItems,clearCart} = useContext(AppContext);

    const [orderDetails, setOrderDetails] = useState(null);

    const [showPopup, setShowPopup] = useState(false);

    const totalAmount = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);

    const tax = totalAmount  * 0.18;

    const grandTotal = totalAmount+ tax;

    const [isProcessing, setIsProcessing] = useState(false);

    const clearAll = () => {
        setCustomerName('');
        setMobileNumber('');
        clearCart();
    }

    const placeOrder = () => {
        setShowPopup(true);
        clearAll();
    }

    const handlePrintReceipt = () => {
        window.print();
    }

    const loadRazorpayReceipt = () => {
        return new Promise((resolve) => {
            const script = document.createElement('script');
            script.src = 'https://checkout.razorpay.com/v1/checkout.js';
            script.async = true;
            script.onload = () => resolve(true);
            script.onerror = () => resolve(false);
            document.body.appendChild(script);
        })
    }

    const deleteOrderOnFailure = async (orderId) => {
        try {
            await deleteOrder(orderId);
        }catch (error) {
            console.log(error);
            toast.error("Error while deleting order");
        }
    }

    const completePayment = async (paymentMode) => {
        if(!customerName || !mobileNumber) {
            toast.error("Please enter customer name and mobile number");
            return;
        }
        if(cartItems.length === 0) {
            toast.error("Cart is empty");
            return;
        }
        const orderData = {
            customerName,
            contactNumber: mobileNumber,
            cartItems,
            subTotal: totalAmount,
            tax,
            grandTotal,
            paymentMethod: paymentMode.toUpperCase()
        }
        setIsProcessing(true);
        try{
            const response = await createOrder(orderData);
            //console.log(response);
            const savedData = response.data;
            if(response.status === 201 && paymentMode === 'cash') {
                toast.success("Order placed successfully");
                setOrderDetails(response.data);
            }
            else if(response.status === 201 && paymentMode === 'upi') {
                const razorpayLoaded = await loadRazorpayReceipt();
                if(!razorpayLoaded) {
                    toast.error("Unable to load razorpay");
                    await deleteOrderOnFailure(savedData.orderId);
                    return;
                }
                //create razorpay order
                const razorpayResponse = await createRazorpayOrder({amount: grandTotal, currency: 'INR'});
                const options = {
                    key: AppConstants.RAZORPAY_KEY_ID,
                    amount: razorpayResponse.data.amount,
                    name: 'Retail Ease',
                    description: 'Order payment',
                    handler: async (response) => {
                        await verifyPaymentHandler(response, savedData);
                    },
                    prefill: {
                        name: customerName,
                        contact: mobileNumber
                    },
                    theme: {
                        color: '#3399cc'
                    },
                    modal: {
                        ondismiss: async () => {
                            await deleteOrderOnFailure(savedData.orderId);
                            toast.error("Payment cancelled");
                        }
                    },
                };
                const rzp = new window.Razorpay(options);
                rzp.on("payment.failed", async (response) => {
                    await deleteOrderOnFailure(savedData.orderId);
                    toast.error("Payment failed");
                    console.log(response.error.description);
                });
                rzp.open();
            }
        } catch (error) {
            console.log(error);
            toast.error("Payment processing failed");
        }
        finally {
            setIsProcessing(false);
        }

        const verifyPaymentHandler = async (response, savedOrder) => {
            const paymentDetails = {
                razorpayOrderId: response.razorpay_order_id,
                razorpayPaymentId: response.razorpay_payment_id,
                razorpaySignature: response.razorpay_signature,
                orderId: savedOrder.orderId

            };
            try{
                const paymentResponse = await verifyRazorpayPayment(paymentDetails);
                toast.success("Payment successful");
                console.log(paymentResponse);
                setOrderDetails({
                    ...savedOrder,
                    paymentDetails: {
                        razorpayOrderId: response.razorpay_order_id,
                        razorpayPaymentId: response.razorpay_payment_id,
                        razorpaySignature: response.razorpay_signature,
                    }
                });
            }catch (error) {
                console.log(error);
                toast.error("Payment failed");
            }
        };
    }

    return (
        <div className="mt-2">
            <div className="cart-summary-details">
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Item: </span>
                    <span className="text-light">₹{totalAmount.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Tax (1%):</span>
                    <span className="text-light">₹{tax.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-4">
                    <span className="text-light">Total:</span>
                    <span className="text-light">₹{grandTotal.toFixed(2)}</span>
                </div>
            </div>

            <div className="d-flex gap-3">
                <button className="btn btn-success flex-grow-1" onClick={() => completePayment("cash")} disabled={isProcessing}>
                    {isProcessing ? "Processing..." : "Cash"}
                </button>
                <button className="btn btn-primary flex-grow-1" onClick={() => completePayment('upi')} disabled={isProcessing}>
                    {isProcessing ? "Processing..." : "UPI"}
                </button>
            </div>
            <div className="d-flex gap-3 mt-3">
                <button className="btn btn-warning flex-grow-1" onClick={placeOrder} disabled={isProcessing}>
                    Place Order
                </button>
                {
                    showPopup && (
                        <ReceiptPopup
                            orderDetails={{
                                ...orderDetails,
                                razorpayOrderId: orderDetails.paymentDetails?.razorpayOrderId,
                                razorpayPaymentId: orderDetails.paymentDetails?.razorpayPaymentId,
                            }}
                            onClose={() => setShowPopup(false)}
                            onPrint={handlePrintReceipt}
                        />
                    )
                }
            </div>
        </div>
    )
}
