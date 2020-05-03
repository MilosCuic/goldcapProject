import React, {Component} from 'react';
import {getOrderById, updateOrder} from "../actions/orderActions";
import {getRealms} from "../actions/realmActions";
import classnames from "classnames";
import PropTypes from "prop-types";
import {connect} from "react-redux";

class UpdateOrder extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: '',
            buyerName: '',
            goldAmount: '',
            price: '',
            realmId: '',
            goldcapUserId: ''
        }

        this._submitForm = this._submitForm.bind(this);
        this.onChange = this.onChange.bind(this);
    }


    componentDidMount() {
        this.props.getRealms();
        const {id} = this.props.match.params;
        this.props.getOrderById(id , this.props.history);
    }

    componentWillReceiveProps(nextProps, nextContext) {

        console.log()
        const order = nextProps.order;

        this.setState({
            buyerName: order.buyerName,
            goldAmount: order.goldAmount,
            price: order.price,
            realmId: order.realmId,
            goldcapUserId: order.goldcapUserId,
        })
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    _submitForm = (e) => {
        e.preventDefault();

        const order = {
            id: this.props.match.params.id,
            buyerName: this.state.buyerName,
            goldAmount: this.state.goldAmount,
            price: this.state.price,
            realmId: this.state.realmId,
            goldcapUserId: this.state.goldcapUserId
        }


        this.props.updateOrder(order , this.props.history)

        this.setState({
            id: '',
            buyerName: '',
            goldAmount: '',
            price: '',
            realmId: '',
            goldcapUserId: ''
        })


    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <hr/>
                            <form onSubmit={this._submitForm}>
                                <div className="form-group">
                                    <input required={true} type="text" className="form-control form-control-md "
                                           placeholder="Buyer name"
                                           name="buyerName"
                                           value={this.state.buyerName}
                                           onChange={this.onChange}/>
                                    {
                                        this.state.errors ?
                                            <span>{this.state.errors.buyerName}</span> : ''
                                    }
                                </div>
                                <div className="form-group">
                                    <input required={true} type="text" className="form-control form-control-md "
                                           placeholder="total gold amount"
                                           name="goldAmount"
                                           value={this.state.goldAmount}
                                           onChange={this.onChange}/>
                                    {
                                        this.state.errors ?
                                            <span>{this.state.errors.goldAmount}</span> : ''
                                    }
                                </div>
                                <div className="form-group">
                                    <input required={true} type="text" className="form-control form-control-md "
                                           placeholder="total price"
                                           name="price"
                                           value={this.state.price}
                                           onChange={this.onChange}/>
                                    {
                                        this.state.errors ?
                                            <span>{this.state.errors.price}</span> : ''
                                    }
                                </div>
                                <div className="form-group">
                                    <label>
                                        select realm:
                                        <select required={true} name="realmId" className="form-control form-control-md " value={this.state.realmId} onChange={this.onChange}>
                                            {
                                                this.props.realms.map((realm) => {
                                                    return (<option value={realm.id}> {realm.name}</option>);
                                                })
                                            }
                                        </select>
                                    </label>
                                    {
                                        this.state.errors ?
                                            <span>{this.state.errors.realmId}</span> : ''
                                    }
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
UpdateOrder.propTypes = {
    getRealms: PropTypes.func.isRequired,
    getOrderById: PropTypes.func.isRequired,
    order: PropTypes.object.isRequired,
    updateOrder: PropTypes.func.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    realms: state.orders.realms,
    order: state.orders.order,
})

export default connect(mapStateToProps , {getRealms , getOrderById , updateOrder}) (UpdateOrder);