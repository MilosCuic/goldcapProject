import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from  "react-redux";
import {createOrder, deleteOrder, getOrders} from "../actions/orderActions";
import {getRealms} from "../actions/realmActions";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

class HomePage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
        }
    };


    componentDidMount() {
       if (this.props.security.isVerified === false){
           this._setShowModal(true);
       }
    }

    componentWillReceiveProps(nextProps, nextContext) {

        alert('ouch');
        if (nextProps.security.isVerified === false){
            this._setShowModal(true);
        }
    }

    _setShowModal(state){
        this.setState({
            showModal: state
        })
    }

    render() {
        return (
            <div>
                HOME PAGE
                <Alert variant="danger"  show={this.state.showModal} onClose={() => this._setShowModal(false)} dismissible>
                    <Alert.Heading>Your account is not verified!</Alert.Heading>
                    <p>
                        To access your resources and other pages , please go to you email
                        and click on confirmation link in order to verify your account.
                    </p>
                </Alert>
            </div>
        );
    }
}

HomePage.propTypes = {
    security: PropTypes.object.isRequired
}


const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps , {}) (HomePage);