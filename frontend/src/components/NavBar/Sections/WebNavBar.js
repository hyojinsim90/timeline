import React from "react"
import styled from "styled-components"
import { Menu, Button } from "antd"
import { Link } from "react-router-dom"

const Logo =  styled.div`
  font-weight: bold;
  font-size: 3.5vh;
  padding: 1rem;
  span {
    color: #f0f0f0;
  }
  a {
    color: black !important;
  }
  + div {
    border-bottom: 1px solid #f0f0f0;
  }
`

const MenuList = styled.div`
  display: flex;
  justify-content: space-between;
  .ant-menu-overflow-item.ant-menu-item {
    position: relative !important;
  }
`


const WebNavBar = (props) => {
  return (
    <>
      <Logo>
        <Link to="/">
          TI<span>MELI</span>NE
        </Link>
      </Logo>
      <MenuList>
        <Menu selectedKeys="mail" mode="horizontal">
          <Menu.Item key="timeline">
            타임라인
          </Menu.Item>
          <Menu.Item key="class">
            클래스
          </Menu.Item>
        </Menu>
        <Menu mode="horizontal">
          <Menu.Item key="mypage">
            <Link to="/mypage">
              마이페이지
            </Link>
          </Menu.Item>
          { props.auth ?
          <>
            <Menu.Item key="logout" onClick={props.onLogout}>
              로그아웃
            </Menu.Item>
          </>
          :
          <>
            <Menu.Item key="login">
              <Link to="/login">
                로그인
              </Link>
            </Menu.Item>
            <Menu.Item key="signup">
              <Link to="/signup">
                회원가입
              </Link>
            </Menu.Item>
          </>
          }
        </Menu>
      </MenuList>
    </>
  )
}

export default WebNavBar
