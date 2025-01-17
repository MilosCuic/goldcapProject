import React, {Component} from 'react';
import {Route , Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';

const SecuredRoute = ({ component:Component , security , ...otherProps }) => (
    <Route {...otherProps}
           render = {
               props => security.isValid === true && security.isVerified?
               (<Component {...props}/>) :
               (<Redirect to="/"/>)
           }/>
)
SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps)(SecuredRoute)