import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from  "react-redux";
import {createRealm} from "../actions/realmActions";

class AddRealm extends Component {

    constructor() {
        super()

        this.state = {
            name: '',
            type: '',
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this._submitForm = this._submitForm.bind(this);
    }

    //life cycle hooks
    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({errors: this.props.errors})
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    _submitForm = (e) => {
        e.preventDefault();
        const newRealm = {
            name: this.state.name,
            type: this.state.type
        }

        this.props.createRealm(newRealm , this.props.history)

        // alert(realm);
        // this.setState({
        //     name: '',
        //     type: ''
        // })
    }

    render() {
        return (
            <div>
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5 className="display-4 text-center">Create / Edit Project form</h5>
                                <hr/>
                                <form onSubmit={this._submitForm}>
                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg "
                                               placeholder="Realm Name"
                                                name="name"
                                                value={this.state.name}
                                               onChange={this.onChange}/>
                                    </div>
                                    <div className="form-group">
                                        <input type="text" className="form-control form-control-lg "
                                               placeholder="Type"
                                                name="type"
                                                value={this.state.type}
                                               onChange={this.onChange}/>
                                    </div>
                                    <input type="submit" className="btn btn-primary btn-block mt-4"/>
                                </form>
                            </div>
                        </div>
                    </div>



            </div>
        );
    }
}

AddRealm.propTypes = {
   createRealm : PropTypes.func.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
})

export default connect(mapStateToProps ,
    {createRealm}
    )(AddRealm);