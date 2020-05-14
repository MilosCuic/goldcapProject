import React, {Component} from 'react';
import {Form, Nav, NavbarBrand, NavLink, FormControl, Button, Navbar} from "react-bootstrap";
import {login, logout} from "../../actions/securityActions";
import PropTypes from "prop-types";
import {connect} from "react-redux";

class Header extends Component {

    constructor(props) {
        super(props);
    }

    logoutUser = () =>{
        this.props.logout();
        window.location.href = "/";
    }

    render() {

        const {isValid} = this.props.security;
        const user = this.props.security.user;
        console.log()

        const userIsAdmin= (
            <Navbar bg="dark" variant="dark">
                <NavbarBrand href="#home">Goldcap project</NavbarBrand>
                <Nav className="mr-auto">
                    <NavLink href="/orders">Orders</NavLink>
                    <NavLink href="/realms">Realms</NavLink>
                    <NavLink href="/admin-dashboard">Admin dashboard</NavLink>
                    <NavLink onClick={this.logoutUser}>Logout</NavLink>
                </Nav>
            </Navbar>
        );

        const userIsAuthenticated= (
            <Navbar bg="dark" variant="dark">
                <NavbarBrand href="#home">Goldcap project</NavbarBrand>
                <Nav className="mr-auto">
                    <NavLink href="/orders">Orders</NavLink>
                    <NavLink href="/realms">Realms</NavLink>
                    <NavLink onClick={this.logoutUser}>Logout</NavLink>
                </Nav>
            </Navbar>
        );

        const userNotAuthenticated = (
            <Navbar bg="dark" variant="dark">
                <NavbarBrand href="#home">Goldcap project</NavbarBrand>
                <Nav className="mr-auto">
                    <NavLink href="/home">Home</NavLink>
                    <NavLink href="/login">Login</NavLink>
                    <NavLink href="/register">Register</NavLink>
                </Nav>
            </Navbar>
        );

        let headerLinks;

        if (isValid && user.isAdmin) {
            headerLinks = userIsAdmin
        }else if (isValid && user) {
            headerLinks = userIsAuthenticated
        }else {
            headerLinks = userNotAuthenticated
        }

        return (
            <div>
                {headerLinks}
            </div>
        );
    }
}

Header.propTypes = {
    logout: PropTypes.func.isRequired,
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
})

export default connect(
    mapStateToProps,
    {logout})
(Header);

