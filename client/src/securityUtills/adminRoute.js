import React, {Component} from 'react';
import {Route , Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';

const AdminRoute = ({ component:Component , security , ...otherProps }) => (
    <Route {...otherProps}
           render = {
               props => security.isValid === true && security.isVerified && security.user.isAdmin ?
                   (<Component {...props}/>) :
                   (<Redirect to="/"/>)
           }/>
)
AdminRoute.propTypes = {
    security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps)(AdminRoute)