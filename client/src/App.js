import React from 'react';
import './App.css';
import Header from "./components/common/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter , Route , Switch} from "react-router-dom";
import Order from "./components/Order";
import Realm from "./components/Realm";
import AddRealm from "./components/AddRealm";
import {Provider} from "react-redux";
import store from "./store";
import UpdateOrder from "./components/UpdateOrder";
import Landing from "./components/layout/Landing";
import Register from "./components/userManagement/Register";
import Login from "./components/userManagement/Login";
import jwt_decode from 'jwt-decode'
import {setJWTToken} from "./securityUtills/setJWTToken";
import {SET_CURRENT_USER} from "./actions/types";
import {logout} from "./actions/securityActions";
import SecuredRoute from "./securityUtills/secureRoute";
import {Redirect} from 'react-router-dom';
import HomePage from "./components/HomePage";
import AdminRoute from "./securityUtills/adminRoute";
import Dashboard from "./components/Dashboard";

const jwt_token = localStorage.getItem("jwtToken");

if (jwt_token) {
    setJWTToken(jwt_token)
    const decode_token = jwt_decode(jwt_token);
    store.dispatch({
        type: SET_CURRENT_USER,
        payload: decode_token
    })
    const currentTime = Date.now()/1000
    console.log('time left: ' , (decode_token.exp - currentTime));
    console.log('current time: ' , currentTime)
    console.log('decode_token.exp' , decode_token.exp)

    if (decode_token.exp < currentTime){
        store.dispatch(logout())
        window.location.href = "/";
    }
}


function App() {
    return (
        <Provider store={store}>
        <BrowserRouter>
            <div className="App">
                <Header/>
                <Route exact path="/" component={Landing}/>
                <Route exact path="/register" component={Register}/>
                <Route exact path="/login" component={Login}/>
                <Route exact path="/home" component={HomePage}/>

                <Switch>
                    {/*for status code 404*/}
                {/*<Route component={HomePage} />*/}
                <SecuredRoute exact path='/realms' component={Realm}/>
                <SecuredRoute exact path='/orders' component={Order}/>
                <SecuredRoute exact path='/addRealm' component={AddRealm}/>
                <SecuredRoute exact path={'/updateOrder/:id'} component={UpdateOrder}/>
                <AdminRoute exact path={'/admin-dashboard'} component={Dashboard}/>
                </Switch>
            </div>
        </BrowserRouter>
        </Provider>
    );
}

export default App;
