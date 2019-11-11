// @flow
import React from 'react';
import { GOOGLE_AUTH_URL } from "../../constants";
import styled from 'styled-components';
import googleLogo from "../../img/googleLogo.svg";

const GoogleImage = styled.div`
  background-image: url(${googleLogo});
  width: 18px;
  height: 18px;
`;

const Button = styled.div`
  background-color: white;
  -webkit-box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.24);
  -moz-box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.24);
  box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.24);
  border-radius: 4px 4px 4px 4px;
  -moz-border-radius: 4px 4px 4px 4px;
  -webkit-border-radius: 4px 4px 4px 4px;
  border: 0px solid #000000;
  height: 35px;
  width: 225px;
  display: flex;
  cursor: pointer;
  margin-left: 9px;
  margin-top: 20%;
  margin-bottom: 20%;
  :hover {
    transform: scale(1.01);
  }
  .image {
    padding-left: 13px;
    padding-top: 9px;
    flex-basis: 10%;
  }
  .text {
    padding-top: 5px;
    padding-left: 27px;
    flex-basis: 80%;
  }
`;

type Props = {
  buttonText: string,
}

const AuthButton = (props: Props) => {

  const onClickGoogleAuth = (e) => {
    e.preventDefault();
    window.open(GOOGLE_AUTH_URL, "_self");
  }

  return(
    <div >
      <Button onClick={(e)=>onClickGoogleAuth(e)}>
        <div className="image"><GoogleImage/></div>
        <div className="text">{props.buttonText} with Google</div>
      </Button>
    </div>
  )
}

export default AuthButton;
