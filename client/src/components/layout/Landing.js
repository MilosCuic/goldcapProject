import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";

class Landing extends Component {

    componentDidMount() {
        if (this.props.security.isValid && this.props.security.isVerified === true){
            this.props.history.push("/orders");
        }
        if (this.props.security.isValid && this.props.security.isVerified === false){
            this.props.history.push("/home");
        }
    }

    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h1 className="display-3 mb-4">Goldcap shitface</h1>
                                <p className="lead">
                                    Create your account to join active projects or start you own
                                </p>
                                <hr/>
                                <a href="/register" className="btn btn-lg btn-primary mr-2">
                                    Sign Up
                                </a>
                                <a href="/login" className="btn btn-lg btn-secondary mr-2">
                                    Login
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Landing.propTypes = {
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps) (Landing);