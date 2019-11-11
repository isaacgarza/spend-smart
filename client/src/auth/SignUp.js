import React, { useEffect } from "react";
import { Link, Redirect } from "react-router-dom";
import AuthButton from './components/authButton';

type Props = {
  authenticated: boolean,
}
const SignUp = (props: Props) => {

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
    <div className="sign-in-container">
      <div className="sign-in-content">
        <h1 className="sign-in-title">Spend Smart</h1>
          <AuthButton buttonText="Sign In"/>
        <div className="or-separator">
          <span className="or-text">OR</span>
        </div>
        <span className="sign-up-link">
         Have an account? <Link to="/sign-in">Sign in!</Link>
        </span>
      </div>
    </div>
  );
}

export default SignUp;
