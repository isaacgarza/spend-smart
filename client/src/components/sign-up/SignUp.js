import React, { Component } from "react";
import "./SignUp.css";
import { Link, Redirect } from "react-router-dom";
import { GOOGLE_AUTH_URL } from "../../constants";
import googleLogo from "../../img/google-logo.png";

class SignUp extends Component {
  render() {
    if (this.props.authenticated) {
      return (
        <Redirect
          to={{
            pathname: "/",
            state: { from: this.props.location }
          }}
        />
      );
    }

    return (
      <div className="signup-container">
        <div className="signup-content">
          <h1 className="signup-title">Sign up with Spend Smart</h1>
          <SocialSignUp />
          <div className="or-separator">
            <span className="or-text">OR</span>
          </div>
          <span className="sign-in-link">
            Already have an account? <Link to="/sign-in">Sign in!</Link>
          </span>
        </div>
      </div>
    );
  }
}

class SocialSignUp extends Component {
  render() {
    return (
      <div className="social-signup">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Sign up with Google
        </a>
      </div>
    );
  }
}

export default SignUp;
