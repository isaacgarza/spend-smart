// @flow
import React from "react";
import { Switch, Route } from "react-router-dom";
import Home from "../components/Home";
import Goals from '../components/Goals'
import SignUp from "../auth/SignUp";
import SignIn from "../auth/SignIn";

const AppRoutes = () => {
  return (
    // The main routes that will be added to the header must be added here
    //and home page has to be added here
    <Switch>
      <Route exact path="/" component={Home} />
      <Route path="/sign-up" component={SignUp} />
      <Route path="/sign-in" component={SignIn} />
      <Route path="/goals" component={Goals}/>
    </Switch>
  );
};

export default AppRoutes;
