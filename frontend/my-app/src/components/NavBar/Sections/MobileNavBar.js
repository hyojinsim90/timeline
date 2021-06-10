import React, { useState } from "react"
import styled from "styled-components"
import { Menu, Button } from "antd"
import { Link } from "react-router-dom"
import { MenuOutlined, MenuFoldOutlined } from "@ant-design/icons"

const LogoM = styled.div`
  font-weight: bold;
  font-size: 3.5vh;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  span {
    color: #f0f0f0;
  }
  a {
    color: black !important;
  }

  Button {
    background: black !important;
    border: none;
  }
`

const MobileNavBar = (props) => {
  const [toggleMenu, setToggleMenu] = useState(false)
  const [toggleBar, setToggleBar] = useState(true)

  const toggleChange = () => {
    setToggleMenu(!toggleMenu)
    setToggleBar(!toggleBar)
  }

  const onMenuClick = () => {
    setToggleMenu(!toggleMenu)
    setToggleBar(!toggleBar)
  }

  return (
    <>
      <LogoM>
        <Link to="/">
          TI<span>MELI</span>NE
        </Link>
        <div >
          <Button type="primary" onClick={toggleChange} style={{ marginBottom: 16 }}>
            { toggleBar ? <MenuOutlined /> : <MenuFoldOutlined /> }
          </Button>
        </div>
      </LogoM>
      { toggleMenu ?
        <Menu
          defaultSelectedKeys={['1']}
          mode="inline"
          theme="light"
          inlineCollapsed={toggleBar}
          onClick={onMenuClick}
        >
          <Menu.Item key="timeline">
            타임라인
          </Menu.Item>
          <Menu.Item key="class">
            클래스
          </Menu.Item>
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
        : <></>
      }
    </>
  )
}

export default MobileNavBar
