import React from 'react';
import './App.css';
import Header from "./components/common/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter , Route} from "react-router-dom";
import Order from "./components/Order";
import Realm from "./components/Realm";
import AddRealm from "./components/AddRealm";
import {Provider} from "react-redux";
import store from "./store";
import UpdateOrder from "./components/UpdateOrder";


function App() {
    return (
        <Provider store={store}>
        <BrowserRouter>
            <div className="App">
                <Header/>
                <Route exact path='/' component={Order}/>
                <Route exact path='/realms' component={Realm}/>
                <Route exact path='/orders' component={Order}/>
                <Route path='/addRealm' component={AddRealm}/>
                <Route path={'/updateOrder/:id'} component={UpdateOrder}/>
            </div>
        </BrowserRouter>
        </Provider>
    );
}

export default App;
