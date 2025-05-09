import MenuBar from "./components/menuBar/menuBar.jsx";
import {Route, Routes, useLocation} from "react-router-dom";
import Dashboard from "./pages/Dashboard/Dashboard.jsx";
import Explore from "./pages/Explore/Explore.jsx";
import ManageItem from "./pages/ManageItem/ManageItem.jsx";
import ManageCategory from "./pages/ManageCategory/ManageCategory.jsx";
import ManageUser from "./pages/ManageUser/ManageUser.jsx";
import {Toaster} from "react-hot-toast";
import Login from "./pages/Login/login.jsx";

function App(){
    const location = useLocation();
    return(
        <div>
            {location.pathname !== "/login" && <MenuBar />}
            <Toaster />
            <Routes>
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/explore" element={<Explore />} />
                <Route path="/items" element={<ManageItem />} />
                <Route path="/category" element={<ManageCategory />} />
                <Route path="/user" element={<ManageUser />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </div>
    );
}

export default App;