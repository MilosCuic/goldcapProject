import React, {Component} from 'react';
import {Form, Nav, NavbarBrand, NavLink, FormControl, Button, Navbar} from "react-bootstrap";

class Header extends Component {
    render() {
        return (
            <div>
                <Navbar bg="dark" variant="dark">
                    <NavbarBrand href="#home">Goldcap project</NavbarBrand>
                    <Nav className="mr-auto">
                        <NavLink href="/home">Home</NavLink>
                        <NavLink href="/orders">Orders</NavLink>
                        <NavLink href="/realms">Realms</NavLink>
                    </Nav>
                </Navbar>
            </div>
        );
    }
}

export default Header;