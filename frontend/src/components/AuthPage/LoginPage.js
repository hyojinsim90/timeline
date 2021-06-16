import React, { useState } from "react"
import { Form, Input, Button } from "antd"
import styled from "styled-components"
import { useHistory } from "react-router-dom"
import { useCookies } from "react-cookie"
import Axios from "axios"
import { Link } from "react-router-dom"

const LoginDiv = styled.div`
  padding: 3rem 0;
  form {
    width: 320px;
    display: inline-block;
    .ant-form-item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      .ant-form-item-label {
        text-align: center;
      }
    }
    // 비밀번호 찾기 영역
    .ant-form-item:nth-child(2) {
      margin-bottom: 0;
      & + .ant-form-item .ant-col {
        text-align: right;
        #search {
          color: lightgray !important;
        }
      }
    }
    label {
      margin-bottom: 1rem;
    }
    .ant-input-password {
      height: 32px;
      input {
        height: 100%;
      }
    }
    button {
      width: 100%;
      background: black;
      color: #ffffff;
      margin-top: 1rem;
    }
  }
`;

const LoginPage = () => {

  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [errorMessage, setErrorMessage] = useState("")
  const [cookies, setCookie] = useCookies("")

  const history = useHistory()

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onChangePwd = (e) => {
    setPassword(e.target.value)
  }

  const onCheckEnter = (e) => {
    if(e.key === "Enter") {
      onLogin()
    }
  }

  const onLogin = () => {

    var variables = {
      "email": email,
      "password": password
    }

    Axios.post("/auth/login", variables)
      .then(res => {
        setCookie("tl_token", res.data.accessToken)
        setCookie("tl_exp", res.data.accessTokenExpiresIn)
        setCookie("tl_e", email)
        setCookie("tl_re", res.data.refreshToken)
        history.push("/mypage")
        window.location.reload()
      })
      .catch(err => {
        setErrorMessage("이메일이나 비밀번호를 다시 확인해 주세요")
      })
  }

  return (
    <LoginDiv>
      <h1>로그인</h1>
      <br />
      <Form onKeyPress={onCheckEnter}>
        <Form.Item
          label="이메일 주소:"
          name="email"
          rules={[{ required: true }]}
        >
          <Input value={email} onChange={onChangeEmail} />
        </Form.Item>
        <Form.Item
          label="비밀번호:"
          name="password"
          rules={[{ required: true }]}
        >
          <Input.Password value={password} onChange={onChangePwd} />
        </Form.Item>
        <Form.Item name="search">
          <Link to="/findpw">
            비밀번호 찾기
          </Link>
        </Form.Item>
        {errorMessage && (
          <label><p style={{ color: '#ff0000bf', fontSize: '0.7rem', border: '1px solid', padding: '1rem', borderRadius: '10px' }}>{errorMessage}</p></label>
        )}
        <Form.Item>
          <Button size="large" onClick={onLogin}>
            로그인
          </Button>
        </Form.Item>
      </Form>
    </LoginDiv>
  )
}

export default LoginPage;
