import React, { useState } from 'react'
import { Input } from 'antd'
import styled from 'styled-components'
import Axios from 'axios'
import { useHistory } from 'react-router-dom'
import { useCookies } from 'react-cookie'


const LoginDiv = styled.div`
  padding: 3rem 0;
  form {
    width: 320px;
    display: inline-block;
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

  const onLogin = () => {

    let variables = {
      "email": email,
      "password": password
    }
setCookie('tn_e', email)
    Axios.post('/auth/login', variables)
      .then(res => {
        setCookie('tn_e', email)
        history.push('/')
      })
  }

  return (
    <LoginDiv>
      <h1>로그인</h1>
      <br />
      <form>
        <label>이메일 주소:</label>
        <Input
          type='email'
          value={email}
          required
          onChange={e => setEmail(e.target.value)}
        />
        <label>비밀번호:</label>
        <Input
          type='password'
          value={password}
          required
          onChange={e => setPassword(e.target.value)}
        />
      <Input type='submit' size="large" value='로그인' onClick={onLogin}/>
      </form>
    </LoginDiv>
  )
}

export default LoginPage;
