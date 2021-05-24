import React, { useState, useEffect } from "react"
import { Row, Col, Card, Button } from "antd"
import { InfoCircleOutlined, FieldTimeOutlined, FundProjectionScreenOutlined, QuestionCircleOutlined } from "@ant-design/icons"
import styled from "styled-components"
import { Link } from "react-router-dom"
import { useSelector } from "react-redux"

const MenuDiv = styled.div`
  padding: 3rem 2rem;
  .ant-row {
    display: flex;
    align-items: center;
    border: 1px solid #f0f0f0;
  }
  .ant-col-sm-24 {
    background: black;
    padding: 12px 0;
    .ant-card {
      width: 100%;
      background: black;
      color: white;
    }
  }
  .ant-col-sm-12 {
    display: flex;
    justify-content: space-around;
    padding: 24px 0;
    font-size: 18px;
  }
  .ant-card {
    border: none;
    .ant-card-body {
      font-size: 18px;
    }
  }
`;

const MenuBar = () => {

  const [nickname, setNickname] = useState("")
  const user = useSelector(state => state.user.userData)

  useEffect(() => {
    if(user !== undefined) {
      setNickname(user.nickname)
    }
  }, [user])

  return (
    <MenuDiv>
      <Row>
        <Col lg={8} sm={24} xs={24}>
          <Card>
            <p>{nickname}님 안녕하세요</p>
            <Button shape="round">정회원</Button>
          </Card>
        </Col>
        <Col lg={8} sm={12} xs={24}>
          <div>
            <Link to="/myinfo">
              <InfoCircleOutlined />
              <p>내 정보 관리</p>
            </Link>
          </div>
          <div>
            <Link to="/mytimeline">
              <FieldTimeOutlined />
              <p>내 타임라인</p>
            </Link>
          </div>
        </Col>
        <Col lg={8} sm={12} xs={24}>
          <div>
            <FundProjectionScreenOutlined />
            <p>내 클래스</p>
          </div>
          <div>
            <QuestionCircleOutlined />
            <p>문의하기</p>
          </div>
        </Col>
      </Row>
    </MenuDiv>
  )
}

export default MenuBar;
