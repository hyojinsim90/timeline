import React, { useState } from 'react'
import { Form, Input, Button } from 'antd'
import styled from 'styled-components'
import Axios from 'axios'
import { useHistory } from 'react-router-dom'
import { useCookies } from 'react-cookie'
import { useDispatch } from 'react-redux'
import { loginUser } from '../../_actions/user_actions'

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
    input {
      margin-bottom: 1.5rem;
      &[type=submit] {
        background: black;
        color: white;
        margin-top: 1rem;
      }
    }
  }
`;

const LoginPage = () => {

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [cookies, setCookie] = useCookies(['tn_e'])

  const history = useHistory()
  const dispatch = useDispatch()

  const onLogin = () => {

    let variables = {
      "email": email,
      "password": password
    }
    //
    // dispatch(loginUser(variables))
    //   .then(res => {
    //     alert(res);
    //     console.log(res);
    //     setCookie('tn_token', res.data.accessToken)
    //     // history.push('/')
    //   })
    Axios.post('/auth/login', variables)
      .then(res => {
        console.log(res);
        // setCookie('tn_token', response.data.accessToken)
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
          <Input onChange={e => setEmail(e.target.value)} />
        </Form.Item>
        <Form.Item
          label="비밀번호:"
          name="password"
          rules={[{ required: true }]}
        >
          <Input.Password onChange={e => setPassword(e.target.value)} />
        </Form.Item>
        <Form.Item>
          <Button onClick={onLogin}>
            로그인
          </Button>
        </Form.Item>
      </Form>
    </LoginDiv>
  )
}

export default LoginPage;
