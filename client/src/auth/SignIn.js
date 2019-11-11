import React, {useEffect } from "react";
import { Link, Redirect } from "react-router-dom";
import AuthButton from './components/authButton';
import {Container} from './components/styles';
import {Card} from 'react-bootstrap';

type Props = {
  authenticated: boolean,
}

const SignIn = (props: Props) => {

  useEffect(() => {
    if (props.authenticated) {
      return (
        <Redirect
          to={{
            pathname: "/",
            state: { from: this.props.location }
          }}
        />
      );
    }
  },[props.authenticated])

  return (
    <Container>
      <Card className="text-center" bg="light" style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>Spend Smart</Card.Title>
          <Card.Text>
            <AuthButton buttonText="Sign In"/>
              Don&apos;t have an account? <Link to="/sign-up">Sign up!</Link>
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default SignIn;
