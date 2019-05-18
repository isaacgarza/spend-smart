import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";

class SignIn extends Component {
  constructor() {
    super();
    this.state = {
      email: "",
      password: ""
    };
  }

  handleEmail = e => {
    this.setState({
      email: e.target.value
    });
  };

  handlePassword = e => {
    this.setState({
      password: e.target.value
    });
  };

  handleOnSubmit = e => {
    alert("Signed Up!");
    e.preventDefault();
    //API to save user to data base in the future
  };

  render() {
    return (
      <Form onSubmit={this.handleOnSubmit}>
        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            value={this.state.email}
            onChange={this.handleEmail}
          />
          <Form.Text className="text-muted">
            We'll sell your email to the highest bidder.
          </Form.Text>
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={this.state.password}
            onChange={this.handlePassword}
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          Sign In
        </Button>
      </Form>
    );
  }
}

export default SignIn;
