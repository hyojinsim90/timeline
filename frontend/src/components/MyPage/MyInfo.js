import React from 'react'
import styled from 'styled-components'
import { Input, Button } from 'antd'

const InfoDiv = styled.div`
  padding: 3rem 0;
  form {
    width: 320px;
    display: inline-block;
    label {
      margin-bottom: 1rem;
    }
    div {
      width: 100%;
      display: flex;
      button {
        margin-left: 10px;
      }
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


const MyInfo = () => {
  return (
    <InfoDiv>
      <form>
        <label>이메일 주소:</label>
        <Input
          type='email'
          disabled
        />
        <label>닉네임:</label><br/>
        <div>
          <Input
            type='password'
            required
          />
          <Button>중복확인</Button>
        </div>
        <label>비밀번호:</label>
        <Input
          type='text'
        />
        <label>비밀번호 확인:</label>
        <Input
          type='text'
        />
      <Input type='submit' size="large" value='수정하기' />
      </form>
    </InfoDiv>
  )
}

export default MyInfo
