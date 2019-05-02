import React, {Component} from 'react';
import {Form, Button, Card} from "react-bootstrap"

const signUpStyle = {
  margin: 'auto',
  width: '30em'
}


class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {
          firstName: "",
          lastName: "",
          phoneNumber: "",
          email: "",
          password: ""
        }
    }

    handleFirstName = e => {
      this.setState({
        firstName: e.target.value
      })
    }
    handleLastName = e => {
      this.setState({
        lastName: e.target.value
      })
    }
    handlePhoneNumber = e => {
      this.setState({
        phoneNumber: e.target.value
      })
    }
    handleEmail = e => {
      this.setState({
        email: e.target.value
      })
    }
    handlePassword = e => {
      this.setState({
        password: e.target.value
      })
    }

    handleOnSubmit = e => {
      alert("Signed Up!");
      e.preventDefault();
      //API to save user to data base in the future
    }


    render() {
        return (
          <Card style={signUpStyle}>
            <Form onSubmit={this.handleOnSubmit}>
              <Form.Group controlId="formFirstName">
                <Form.Label>First Name</Form.Label>
                <Form.Control type="firstName" placeholder="Enter First Name" value={this.state.firstName} onChange={this.handleFirstName}/>
              </Form.Group>
              <Form.Group controlId="formLastName">
                <Form.Label>Last Name</Form.Label>
                <Form.Control type="lastName" placeholder="Enter Last Name" value={this.state.lastName} onChange={this.handleLastName}/>
              </Form.Group>
              <Form.Group controlId="formPhoneNumber">
                <Form.Label>Phone Number</Form.Label>
                <Form.Control type="phoneNumber" value={this.state.phoneNumber} onChange={this.handlePhoneNumber}/>
              </Form.Group>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" placeholder="Enter email" value={this.state.email} onChange={this.handleEmail}/>
                <Form.Text className="text-muted">
                  We'll never share your email with anyone else.
                </Form.Text>
              </Form.Group>
              <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" value={this.state.password} onChange={this.handlePassword}/>
              </Form.Group>
              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>
          </Card>
        );
    }
}

export default SignUp;
