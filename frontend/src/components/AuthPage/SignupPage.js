import React, { useState } from 'react';
import { Input } from 'antd';
import styled from 'styled-components';

const SignupDiv = styled.div`
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

const SignupPage = () => {
  const [email, setEmail] = useState('')
  const [password1, setPassword1] = useState('')
  const [password2, setPassword2] = useState('')
  const [errors, setErrors] = useState(false)

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onChangePwd1 = (e) => {
    setPassword1(e.target.value)
  }

  const onChangePwd2 = (e) => {
    setPassword2(e.target.value)
  }

  return (
    <SignupDiv>
      <h1>회원가입</h1>
      <br />
      <form>
        <label htmlFor='email'>이메일 주소:</label>
        <Input
          type='email'
          value={email}
          onChange={onChangeEmail}
          required
        />
        <label htmlFor='password1'>비밀번호(소문자, 숫자, 특수문자 포함 8~16자):</label>
        <Input
          type='password'
          value={password1}
          onChange={onChangePwd1}
          minLength='8'
          pattern='^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{8,16}$'
          required
        />
        <br />
        <label htmlFor='password2'>비밀번호 확인(소문자, 숫자, 특수문자 포함 8~16자):</label>
        <Input
          type='password'
          value={password2}
          onChange={onChangePwd2}
          minLength='8'
          pattern='^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{8,16}$'
          required
        />
        <Input type='submit' size="large" value='가입하기' />
      </form>
    </SignupDiv>
  )
}

export default SignupPage;
