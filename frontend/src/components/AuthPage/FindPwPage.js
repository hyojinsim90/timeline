import React, { useState } from "react"
import styled from "styled-components"
import { Form, Input, Button } from "antd"
import Axios from "axios"

const FindPwDiv = styled.div`
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
      margin-bottom: 1rem;
    }
    p {
      text-align: left;
    }
  }
`

const FindPwPage = () => {

  const [email, setEmail] = useState("")

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onFindPw = () => {
    let variable = {
      "email": email
    }
    Axios.post("/auth/findPw/sendmail", variable)
      .then(res => {
        console.log(res);
      })
  }

  return (
    <FindPwDiv>
      <h1>비밀번호 찾기</h1>
      <br />
      <Form >
        <Form.Item
          label="이메일 주소:"
          name="email"
          rules={[{ required: true }]}
        >
          <Input onChange={onChangeEmail} />
        </Form.Item>
        <Form.Item>
          <Button size="large" onClick={onFindPw}>
            비밀번호 찾기
          </Button>
          <p>비밀번호 찾기 버튼을 클릭하시면 바뀐 비밀번호가 입력하신 이메일로 전송됩니다.</p>
        </Form.Item>
      </Form>
    </FindPwDiv>
  )
}

export default FindPwPage
