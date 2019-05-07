import React, { Component } from 'react';
import './Goals.css';
import {Table, Card, Row, Col, Jumbotron, ProgressBar} from 'react-bootstrap'


class Goals extends Component {

  constructor() {
    super();
    this.state = {
      userData: null
    }
  }
  componentDidMount() {
    this.setState({
      userData: {
        data:{
          totalAmount: 30000,
          goals: [
            {
              name: "Car",
              goalAmount: 10000,
              savedAmount: 500
            },
            {
              name: "House",
              goalAmount: 20000,
              savedAmount: 8000
            }
          ]
      }}
    })
  }
  render() {
    return (
        <div className="Goals">
          {this.state.userData &&
            <Card className="goalTable">
              <Row>
                <Col>
                  <Jumbotron>
                    <h1>Total Goals Amount</h1>
                    <h1>{this.state.userData.data.totalAmount}</h1>
                  </Jumbotron>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Table striped bordered hover responsive>
                    <thead>
                      <tr>
                        <th>Goal</th>
                        <th>Amount Saved</th>
                      </tr>
                    </thead>
                    <tbody>
                        {this.state.userData && this.state.userData.data.goals.map((goal, key) =>
                          <tr key={key}>
                            <th>{goal.name}</th>
                            <th><ProgressBar variant="success" now={goal.savedAmount} max={goal.goalAmount} label={`${goal.savedAmount}/${goal.goalAmount}`}/></th>
                          </tr>
                          )}
                    </tbody>
                  </Table>
                </Col>
              </Row>
            </Card>}
      </div>
    );
  }
}

export default Goals;
