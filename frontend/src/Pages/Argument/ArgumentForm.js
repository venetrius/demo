import React from 'react';
import { Button, Form, Input, List, Space } from 'antd';
import { DragOutlined, DeleteOutlined } from '@ant-design/icons';
import { sortableContainer, sortableElement, sortableHandle } from 'react-sortable-hoc';
import { arrayMoveImmutable } from 'array-move';
const { TextArea } = Input;

const DragHandle = sortableHandle(() => (
  <DragOutlined style={{ cursor: 'grab', color: '#999', marginRight: '10px' }} />
));

const SortableItem = sortableElement(props => <List.Item {...props} />);
const SortableContainer = sortableContainer(props => <List {...props} />);

const ArgumentForm = ({ addArgument }) => {
  const [form] = Form.useForm();
  const [details, setDetails] = React.useState([]);
  const [key, setKey] = React.useState(0);
  const [detail, setDetail] = React.useState('');

  const onSubmit = ({ title }) => {
    addArgument({ title, details: details.map(detail => detail.text) });
    form.resetFields();
    setDetails([]);
  };

  const handleDetailChange = (e) => {
    setDetail(e.target.value);
  };

  const addDetail = () => {
    const newDetail = detail;
    if (newDetail) {
      setDetails([...details, { id: new Date(), text: newDetail }]);
      form.resetFields(['detail']);
    }
    setDetail("")
  };

  const onSortEnd = ({ oldIndex, newIndex }) => {
    setDetails(arrayMoveImmutable(details, oldIndex, newIndex));
    setKey(prevKey => prevKey + 1);
  };

  const remove = index => {
    const newDetails = [...details];
    newDetails.splice(index, 1);
    setDetails(newDetails);
  };

  return (
    <Form form={form} onFinish={onSubmit} key={key}>
      <Form.Item
        name="title"
        rules={[{ required: true, message: 'Please input the title of the argument!' }]}
      >
        <Input placeholder="Title" />
      </Form.Item>

      <Form.Item
        name="detail"
      >
        <Input.TextArea
          placeholder="Add a detail"
          autoSize={{ minRows: 2, maxRows: 6 }}
          onChange={handleDetailChange}
        />
        <Button onClick={addDetail}>Add</Button>
      </Form.Item>

      {details.length > 0 && (
        <SortableContainer onSortEnd={onSortEnd} useDragHandle>
          {details.map((detail, index) => (
            <SortableItem key={`item-${index}`} index={index}>
              <List.Item
                actions={[
                  <a key="delete" onClick={() => remove(index)}>
                    <DeleteOutlined />
                  </a>,
                ]}
                style={{ padding: '8px' }}
              >
                <Space>
                  <DragHandle />
                  <Form.Item
                    name={`details[${detail.id}]`}
                    initialValue={detail.text}
                    rules={[{ required: true, message: 'detail is required' }]}
                    style={{ marginBottom: 0 }}
                  >
                    <TextArea 
                      autoSize={{ minRows: 2, maxRows: 6 }}
                      style={{ minWidth: '400px' }}
                    />
                  </Form.Item>
                </Space>
              </List.Item>
            </SortableItem>
          ))}
        </SortableContainer>
      )}

      <Form.Item style={{ marginTop: '16px' }}>
        <Button type="primary" htmlType="submit">
          Submit Argument
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ArgumentForm;
