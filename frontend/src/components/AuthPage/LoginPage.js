import React, { useState } from 'react'
import { Form, Input, Button } from 'antd'
import styled from 'styled-components'
import { useHistory } from 'react-router-dom'
import { useCookies } from 'react-cookie'
import Axios from 'axios'

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

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [cookies, setCookie] = useCookies('')

  const history = useHistory()

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onChangePwd = (e) => {
    setPassword(e.target.value)
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
        history.push("/mypage")
        window.location.reload()
      })
  }

  return (
    <LoginDiv>
      <h1>로그인</h1>
      <br />
      <Form>
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
