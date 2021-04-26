import React, { useState } from 'react';
import { Input } from 'antd';
import styled from 'styled-components';

const LoginDiv = styled.div`
  padding: 3rem;
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
  const [errors, setErrors] = useState(false)

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
        <Input type='submit' size="large" value='로그인' />
      </form>
    </LoginDiv>
  )
}

export default LoginPage;
