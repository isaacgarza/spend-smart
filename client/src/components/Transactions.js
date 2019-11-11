// @flow
import React from 'react';
import Table from "react-bootstrap/Table";
import axios from 'axios';

class Transactions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: null,
            error: null
        }
    }

    componentDidMount() {
        let self = this;
        axios.get('/transactions', {
            headers: {'Accept': 'application/json'}
        }).then(function (response) {
            self.setState({
                transactions: response.data.transactions.transactions
            })
        }).catch(function (error) {
            self.setState({error: error})
        })
    }

    render() {
        return (
            <div>
                {(this.state.error && <div>ERROR</div>)}
                {(this.state.transactions && <Table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Amount</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.transactions.map((transaction) =>
                        <tr key={transaction.transaction_id}>
                            <td>{transaction.name}</td>
                            <td>{transaction.amount}</td>
                            <td>{transaction.date}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>)}
            </div>);
    }
}

export default Transactions;
