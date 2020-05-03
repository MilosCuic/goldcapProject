import React, {Component} from 'react';
import {Button, Col , Row , Table , Modal} from "react-bootstrap";
import TableHeader from "./common/TableHeader";
import PropTypes from "prop-types";
import {connect} from  "react-redux";
import {getOrders , createOrder , deleteOrder} from "../actions/orderActions";
import {getRealms} from "../actions/realmActions";
import { bindActionCreators } from 'redux';
import {Link} from "react-router-dom";

class Order extends Component {

    constructor(props) {
        super(props);
        this.handleLogout = this.handleLogout.bind(this);
        this._addNewOrder = this._addNewOrder.bind(this);
        this._submitForm = this._submitForm.bind(this);
        this.onChange = this.onChange.bind(this);

        const realms = this.props.orders.realms;

        this.state = {
            buyerName: '',
            goldAmount: '',
            price: '',
            realmId: '',
            showModal: false,
            realms: realms,
            errors: null
        }
    };

    componentDidMount() {
        this.props.getOrders();
        this.props.getRealms();
    }

    componentWillReceiveProps(nextProps, nextContext) {
        console.log('next Props' , nextProps)
        if (nextProps.errors && Object.keys(nextProps.errors).length > 0) {
            this.setState({
                errors: nextProps.errors
            })
        }
    }

    handleLogout(e) {
        this.props.history.push("/addRealm");
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    _submitForm = (e) => {
        e.preventDefault();

        if (this.state.errors !== null){
        return null;
        }

        const order = {
            buyerName: this.state.buyerName,
            goldAmount: this.state.goldAmount,
            price: this.state.price,
            realmId: this.state.realmId,
            goldcapUserId: 1
        }
        console.log('saved order' , order)
        this.props.createOrder(order , this.props.history)

        this.setState({
            buyerName: '',
            goldAmount: '',
            price: '',
            realmId: '',
            showModal: false
        })


    }

    _addNewOrder() {
        this.handleShow();
    }

     handleClose = () => this.setState({showModal: false});
     handleShow = () => this.setState({showModal: true});

    render() {
        return (
            <div>
                <h1>welcome to the Orders</h1>
                <Row>
                    <Col sm={2} md={2} lg={2}>
                        <Button onClick={this._addNewOrder}>Add new Order</Button>
                    </Col>
                    <Col sm={10} md={10} lg={10}>
                    </Col>
                </Row>
                <br/>
                <Row className="content-table">
                    <Col md={{size: 12, offset: 0}}>
                        <div className="table-wrapper" id="wrapper" ref={_ => (this.pane = _)}>
                            <Table hover size="sm" className="targets-table">
                                <thead>
                                <tr>
                                    {/*<th></th>*/}
                                    <TableHeader title="#" sortKey="active"
                                                />
                                    <TableHeader title="Buyer" sortKey="locked"
                                                />
                                    <TableHeader title='Amount' sortKey="label"
                                                 />
                                    <TableHeader title='Price' sortKey="date"
                                                 />
                                    <TableHeader title='Realm' sortKey="date"
                                    />
                                    <th></th>
                                    {/*<TableHeader title='Folders' sortKey="folders"*/}
                                    {/*             sortedBy={this.state.sortBy} sort={this._sort}/>*/}
                                    {/*<TableHeader title='Images' sortKey="images"*/}
                                    {/*             sortedBy={this.state.sortBy} sort={this._sort}/>*/}
                                    {/*<TableHeader title='Status' sortKey="active"*/}
                                    {/*             sortedBy={this.state.sortBy} sort={this._sort}/>*/}
                                    {/*<TableHeader title='Path' sortKey="path"*/}
                                    {/*             sortedBy={this.state.sortBy} sort={this._sort}/>*/}
                                </tr>
                                </thead>
                                <tbody>
                                {this.props.orders.orders ? this.props.orders.orders.map((order, index) => {
                                     {
                                        return (
                                            <tr key={index++}>
                                                <td>{order.id}</td>
                                                <td>{order.buyerName}</td>
                                                <td>{order.goldAmount}</td>
                                                <td>{order.price}</td>
                                                <td>{order.realmName}</td>
                                                <td><Button onClick={() => { this.props.deleteOrder(order.id)}}>delete</Button></td>
                                                <td><Link to={`/updateOrder/${order.id}`}><Button>edit</Button></Link></td>
                                            </tr>
                                        );
                                    }
                                }) : ''}
                                </tbody>
                            </Table>
                        </div>
                    </Col>
                </Row>


                <Modal show={this.state.showModal} onHide={this.handleClose} size={'lg'}>
                    <Modal.Header closeButton>
                        <Modal.Title>Add new order</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
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
                                                        this.props.orders.realms.map((realm) => {
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
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={this.handleClose}>
                            Cancel
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

Order.propTypes = {
    orders: PropTypes.object.isRequired,
    getOrders : PropTypes.func.isRequired,
    createOrder: PropTypes.func.isRequired,
    getRealms: PropTypes.func.isRequired,
    // deleteOrder: PropTypes.func.isRequired,
}

const mapStateToProps = state => ({
    orders: state.orders,
    errors: state.errors,
    realms: state.realms
})

// const mapDispatchToProps = (dispatch) => {
//     return { actions: bindActionCreators(createOrder, dispatch)}
// }

export default connect(mapStateToProps , {getOrders , createOrder , getRealms ,deleteOrder}) (Order);