import React, { useState, useEffect } from "react"
import styled from "styled-components"
import { Form, Input, Button } from "antd"
import { useSelector } from "react-redux"
import Axios from "axios"
import MenuBar from "./Sections/MenuBar"

const InfoDiv = styled.div`
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
`


const MyInfo = () => {

  const [email, setEmail] = useState("")
  const [nickname, setNickname] = useState([])
  const [password1, setPassword1] = useState("")
  const [password2, setPassword2] = useState("")
  const [existingPw, setExistingPw] = useState("")
  const [checkDuplicate, setCheckDuplicate] = useState(false)
  const [checkValid, setCheckValid] = useState(true)

  const user = useSelector(state => state.user.userData)

  useEffect(() => {
    if(user !== undefined) {
      setEmail(user.email)
      setNickname(user.nickname)

      Axios.get(`/member/${user.email}`)
        .then(res => {
          setExistingPw(res.data.password)
        })
    }
  }, [user])

  const onChangeNick = (e) => {
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
    Axios.get("/member/ids")
      .then(res => {
        const duplicate = res.data.filter(item => item.nickname === nickname)
        // 닉네임 중복일 경우
        if(duplicate.length > 0) {
          // 다른 사람 닉네임과 중복일 경우
          if(nickname !== user.nickname) {
            setCheckDuplicate(false)
            alert("이미 사용 중인 닉네임입니다")
          // 내 기존 닉네임일 경우
          } else {
            setCheckDuplicate(true)
            alert("사용할 수 있는 닉네임입니다")
          }
        } else {
          setCheckDuplicate(true)
          alert("사용할 수 있는 닉네임입니다")
        }
      })
  }

  const onChangeInfo = () => {

    // 닉네임 중복확인 버튼 클릭하도록 하기
    if(checkDuplicate === false) {
      alert("닉네임 중복확인을 해주세요")
      return false
    } else if(password1 || password2) {
      if(password1 !== password2) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다")
        return false
      }
    }

    const variables = {
      "nickname": nickname,
      "password": password1 ? password1 : existingPw
    }

    if(checkDuplicate && checkValid) {
      Axios.put(`/member/${email}`, variables)
        .then(res => {
          // 닉네임, 비밀번호 변경 성공 시 페이지 새로고침
          if(res.status === 200) {
            window.location.reload()
          }
        })
    }
  }

  return (
    <div>
      <MenuBar />
      <InfoDiv>
        <Form>
          <Form.Item
            label="이메일:"
            name="email"
            rules={[{ required: true }]}
          >
            <div>
              { email &&
                <Input value={email} disabled />
              }
            </div>
          </Form.Item>
          <Form.Item
            label="닉네임:"
            name="nickname"
            rules={[{ required: true }]}
          >
            <div>
              <Input value={nickname} onChange={onChangeNick} />
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
            <Input.Password value={password2} onChange={onChangePwd2} />
          </Form.Item>
          <Form.Item
            name="button"
          >
            <Button size="large" onClick={onChangeInfo}>
              수정하기
            </Button>
          </Form.Item>
        </Form>
      </InfoDiv>
    </div>
  )
}

export default MyInfo
