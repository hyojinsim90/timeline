import React from 'react';
import styled from 'styled-components';
import { Input, Select } from 'antd';
import { Link } from 'react-router-dom';

const { TextArea } = Input;

const CreateTimelineDiv = styled.div`
  padding: 3rem 0;
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
    .ant-select {
      width: 100%;
      margin-bottom: 1.5rem;
    }
    .ant-input {
      margin-bottom: 1.5rem;
    }
  }
`;

const { Option } = Select;

const CreateTimeline = () => {
  return (
    <CreateTimelineDiv>
      <h1>타임라인 생성하기</h1>
      <br />
      <form>
        <label>제목</label>
        <Input
          type='text'
          required
        />
        <label>분야</label>
        <Select defaultValue="경제">
          <Option value="economic">경제</Option>
        </Select>
        <label>내용</label>
        <TextArea
          autoSize={{ minRows: 6, maxRows: 6 }}
        />
        <label>공개 여부</label>
        <Select defaultValue="비공개">
          <Option value="private">비공개</Option>
          <Option value="public">공개</Option>
        </Select>
        <Link to="/timeline">
          <Input type='submit' size="large" value='생성하기' />
        </Link>
      </form>
    </CreateTimelineDiv>
  )
}
export default CreateTimeline;
