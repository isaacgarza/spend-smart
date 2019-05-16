import React from "react";
import { Jumbotron, Container } from "react-bootstrap";

export default function Home() {
  return (
    <div>
      <Jumbotron fluid="fluid">
        <Container>
          <h1>SPEND SMART</h1>
          <p>
            Spend smarter with Spend Smart! You will never have to use another
            budgeting application again!
          </p>
        </Container>
      </Jumbotron>
    </div>
  );
}
