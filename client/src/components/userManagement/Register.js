import React, {Component} from 'react';
import {createNewUser} from "../../actions/securityActions";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import classnames from "classnames";

class Register extends Component {

    constructor() {
        super();

        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            username: '',
            password: '',
            confirmedPassword: '',
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this._submitForm = this._submitForm.bind(this);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        if (nextProps.errors){
            this.setState({
                errors: nextProps.errors
            })
        }
    }

    componentDidMount() {
        if (this.props.security.isValid){
            this.props.history.push("/");
        }
    }

    _submitForm(e) {
        e.preventDefault();
        const newUser = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            username: this.state.username,
            password: this.state.password,
            confirmedPassword: this.state.confirmedPassword
        }

        this.props.createNewUser(newUser , this.props.history);
    }

    onChange(e){
        this.setState({
            [e.target.name] : e.target.value
        })
    }



    render() {

        const {errors} = this.state;

        return (
            <div>
                <div className="register">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h1 className="display-4 text-center">Sign Up</h1>
                                <p className="lead text-center">Create your Account</p>
                                <form onSubmit={this._submitForm}>

                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg" placeholder="first name"
                                               name="firstName"
                                               value={this.state.firstName}
                                               onChange={this.onChange}
                                               />
                                    </div>
                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg" placeholder="last name"
                                               name="lastName"
                                               value={this.state.lastName}
                                               onChange={this.onChange}
                                               />
                                    </div>

                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg" placeholder="Username"
                                               name="username"
                                               value={this.state.username}
                                               onChange={this.onChange}
                                               required/>
                                        <span>
                                             {
                                                 <p>{errors.username}</p>
                                             }
                                         </span>
                                    </div>
                                    <div className="form-group">
                                        <input type="email" className="form-control form-control-lg"
                                               placeholder="Email Address"
                                               name="email"
                                               value={this.state.email}
                                               onChange={this.onChange}
                                               required
                                        />
                                        <span>
                                             {
                                                 <p>{errors.email}</p>
                                             }
                                         </span>
                                    </div>
                                    <div className="form-group">
                                        <input type="password" className="form-control form-control-lg"
                                               placeholder="Password"
                                               name="password"
                                               value={this.state.password}
                                               onChange={this.onChange}
                                               required
                                        />
                                        <span>
                                             {
                                                 <p>{errors.password}</p>
                                             }
                                         </span>
                                    </div>
                                    <div className="form-group">
                                        <input type="password" className="form-control form-control-lg"
                                               placeholder="Confirm Password"
                                               name="confirmedPassword"
                                               value={this.state.confirmedPassword}
                                               onChange={this.onChange}
                                               required
                                        />
                                        <span>
                                             {
                                                 <p>{errors.confirmedPassword}</p>
                                             }
                                         </span>
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

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired,
}

const mapStateToProps = state => ({
    errors: state.errors,
    security: state.security
});

export default connect(
    mapStateToProps,
    {createNewUser})
    (Register);