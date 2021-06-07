import React, { useState } from "react"
import { Form, Input, Button } from "antd"
import styled from "styled-components"
import Axios from "axios"
import { useHistory } from "react-router-dom"

const SignupDiv = styled.div`
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
    // 닉네임 영역
    #nickname {
      display: flex;
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
    .ant-form-item:last-child {
      button {
        width: 100%;
        background: black;
        color: #ffffff;
        margin-top: 1rem;
      }
    }
  }
`;

const SignupPage = () => {
  const [email, setEmail] = useState("")
  const [nickname, setNickname] = useState("")
  const [password1, setPassword1] = useState("")
  const [password2, setPassword2] = useState("")
  const [checkDuplicate, setCheckDuplicate] = useState(false)

  const history = useHistory()

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onChangeNickname = (e) => {
    setNickname(e.target.value)
    setCheckDuplicate(false)
  }

  const onChangePwd1 = (e) => {
    setPassword1(e.target.value)
  }

  const onChangePwd2 = (e) => {
    setPassword2(e.target.value)
  }

  // 중복확인 체크
  const onCheckDup = () => {
    Axios.get("/auth/nicknames")
      .then(res => {
        const duplicate = res.data.filter(item => item.nickname === nickname)
        // 닉네임 중복일 경우
        if(duplicate.length > 0) {
          alert("이미 사용 중인 닉네임입니다")
        } else {
          setCheckDuplicate(true)
          alert("사용할 수 있는 닉네임입니다")
        }
      })
  }

  const onSignup = () => {
    const variables = {
      "email": email,
      "nickname": nickname,
      "password": password2
    }

    // 닉네임 중복확인 필수
    if(checkDuplicate) {
      Axios.get(`/auth/findPw/checkmail/${email}`)
        .then(res => {
          // 이메일 중복 없을 경우에만 회원가입 후 로그인 페이지로 이동
          if(res.data === false) {
            Axios.post("/auth/signup", variables)
              .then(response => {
                history.push("/login")
              })
          // 이메일 중복 있을 경우 alert
          } else {
            alert("사용할 수 없는 이메일입니다")
          }
        })
    // 닉네임 중복확인 안 했을 경우
    } else {
      alert("닉네임 중복확인을 해주세요")
    }
  }

  return (
    <SignupDiv>
      <h1>회원가입</h1>
      <br />
      <Form onSubmit={onSignup}>
        <Form.Item
          label="이메일 주소:"
          name="email"
          rules={[{ required: true }]}
        >
          <Input onChange={onChangeEmail} />
        </Form.Item>
        <Form.Item
          label="닉네임:"
          name="nickname"
          rules={[{ required: true }]}
        >
          <div>
            <Input onChange={onChangeNickname} />
            <Button onClick={onCheckDup}>중복확인</Button>
          </div>
        </Form.Item>
        <Form.Item
          label="비밀번호(8~16자):"
          name="password1"
          rules={[{ required: true, min: 8, max: 16, message: '다시 입력해 주세요' }]}
        >
          <Input.Password value={password1} onChange={onChangePwd1} />
        </Form.Item>
        <Form.Item
          label="비밀번호 확인(8~16자):"
          name="password2"
          rules={[{ required: true, min: 8, max: 16, message: '다시 입력해 주세요' }]}
        >
          <Input.Password value={password2} onChange={onChangePwd2}  />
        </Form.Item>
        <Form.Item>
          <Button size="large" onClick={onSignup}>
            가입하기
          </Button>
        </Form.Item>
      </Form>
    </SignupDiv>
  )
}

export default SignupPage;
