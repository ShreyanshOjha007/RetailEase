import './OrderHistory.css'
import {useEffect, useState} from "react";
import {fetchRecentOrders} from "../../Service/OrderService.js";
import toast from "react-hot-toast";

export default function OrderHistory() {

    const [orders, setOrders] = useState([]);

    const [loading, setLoading] = useState(true);

   // const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetchRecentOrders();
                console.log(response);
                setOrders(response.data);
            }catch (error) {
                console.error(error);
                toast.error("Unable to fetch orders");
            }finally {
                setLoading(false);
            }
        }
        fetchOrders();
    },[])

    const formatItems = (items) => {
        return items.map(item => `${item.name} x ${item.quantity}`).join(', ');
    }

    const formatDate = (dateString) => {
        const options= {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        }
        return new Date(dateString).toLocaleDateString('en-US', options);
    }

    if(loading) {
        return(
            <div className="text-center py-4">
                Loading orders....
            </div>
        )
    }

    if(orders.length === 0 && !loading) {
        return(
            <div className="text-center py-4">
                No orders found..
            </div>
        )
    }
    return(
        <div className="order-history-container">
            <h2 className="md-2 text-light">All Orders</h2>
            <div className="table-responsive">
                <table className="table table-striped table-hover">
                    <thead className="table-dark">
                    <tr>
                        <th>Order Id</th>
                        <th>Customer</th>
                        <th>Items</th>
                        <th>PaymentMode</th>
                        <th>Status</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map(order => (
                        <tr key={order.orderId}>
                            <td>{order.orderId}</td>
                            <td>{order.customerName} <br/>
                                <small className="text-muted">{order.contactNumber}</small>
                            </td>
                            <td>{formatItems(order.items)}</td>
                            <td>{order.paymentMethod}</td>
                            <td>
                                <span className={`badge ${order.paymentDetails?.paymentStatus === "COMPLETED"? "bg-success" : "bg-warning text-dark"}`}>
                                    {order.paymentDetails?.paymentStatus || "PENDING"}
                                </span>
                            </td>
                            <td>{formatDate(order.createdAt)}</td>

                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}