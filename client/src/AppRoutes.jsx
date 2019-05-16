import React from "react";
import { Switch, Route } from "react-router-dom";
import Home from "./components/Home";
import SignUp from "./components/SignUp";
import SignIn from "./components/SignIn";

const AppRoutes = () => {
  return (
    //The main routes that can be access through header
    //and home page has to be added here
    <Switch>
      <Route exact path="/" component={Home} />
      <Route path="/signup" component={SignUp} />
      <Route path="/signin" component={SignIn} />
    </Switch>
  );
};

export default AppRoutes;
