import React, { useState, useEffect } from 'react'
import styled from 'styled-components'
import { Input, Button } from 'antd'
import { useSelector } from "react-redux"
import Axios from "axios"

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

  const [email, setEmail] = useState("")
  const [nicknamelist, setNicknameList] = useState([])
  const [nickname, setNickname] = useState([])
  const user = useSelector(state => state.user.userData)

  useEffect(() => {
    if(user !== undefined) {
      setEmail(user.email)
    }
    Axios.get("/member/ids")
      .then(res => {
        res.data.forEach((item, i) => {
          setNicknameList(item.nickname)
        })
      })
  }, [user])

  const onChangeNick = (e) => {
    setNickname(e.target.value)
  }

  // 중복확인 체크
  const onCheckDup = () => {

  }

  return (
    <InfoDiv>
      <form>
        <label>이메일 주소:</label>
        <Input
          type='email'
          value={email}
          disabled
        />
        <label>닉네임:</label><br/>
        <div>
          <Input
            type="text"
            value={nickname}
            onChange={onChangeNick}
            required
          />
          <Button onClick={onCheckDup}>중복확인</Button>
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
