import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {createNewUser, login} from "../../actions/securityActions"


class Login extends Component {

    constructor() {
        super();

        this.state = {
            username: '',
            password: '',
            errors: {}
        }

        this._onChange = this._onChange.bind(this);
        this._submitForm = this._submitForm.bind(this);
    }

    componentDidMount() {
        if (this.props.security.isValid) {
            this.props.history.push('/orders')
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {

        if (nextProps.security.isValid && nextProps.security.isVerified === false) {
            this.props.history.push("/home");
        }

        if (nextProps.security.isValid && nextProps.security.isVerified) {
            this.props.history.push("/orders");
        }

        if (nextProps.errors){
            this.setState({
                errors: nextProps.errors
            })
        }
    }

    _submitForm(e) {
        e.preventDefault();

        const loginRequest = {
            username: this.state.username,
            password: this.state.password,
        }

        this.props.login(loginRequest , this.props.history);
    }

    _onChange(e){
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    render() {
        const {errors} = this.state;
        return (
            <div>
                <div className="login">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h1 className="display-4 text-center">Log In</h1>
                                <form onSubmit={this._submitForm}>
                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg"
                                               placeholder="Username"
                                               name="username"
                                                value={this.state.username}
                                                onChange={this._onChange}/>
                                        {
                                            <span><p>{errors.username}</p></span>
                                        }
                                    </div>
                                    <div className="form-group">
                                        <input type="password" className="form-control form-control-lg"
                                               placeholder="Password"
                                               name="password"
                                               value={this.state.password}
                                               onChange={this._onChange}/>
                                        {
                                            <span><p>{errors.username}</p></span>
                                        }
                                    </div>
                                    <input type="submit" className="btn btn-info btn-block mt-4"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    security: state.security
});

export default connect(
    mapStateToProps,
    {login})
(Login);
