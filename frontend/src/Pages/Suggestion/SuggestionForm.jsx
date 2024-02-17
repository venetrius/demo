import React, { useState } from 'react';
import { Form, Input, Button, Select, InputNumber, message } from 'antd';
import { useArguments } from '../../contexts/ArgumentContext';

const { Option } = Select;
const { TextArea } = Input;

const SuggestionForm = (props) => {
    const [form] = Form.useForm();
    const { addSuggestion } = useArguments();
    const [suggestionType, setSuggestionType] = useState("REVISION")

    const handleSubmit = async (values) => {
        console.log('Received values of form:', values);
        const sucess = await addSuggestion(props.argument.id, values)
        if (sucess) {
            form.resetFields();
            message.success('Suggestion submitted successfully');
        } else {
            message.error('Error submitting suggestion');
        }
    };
    if (!props.argument) return "Loading..."

    const renderSelectSection = () => {
        return (
            <Select>
                {props.argument.argumentDetails.map((detail, index) => (
                    <Select.Option key={index} value={index}>
                        {`${index + 1} - ${detail.slice(0, 30)}...`}
                    </Select.Option>
                ))}
            </Select>
        )
    }

    return (
        <Form form={form} layout="vertical" onFinish={handleSubmit}
            initialValues={{ type: "REVISION", argumentId: props.argument.id, argumentVersion: 1 }}
        >
            <Form.Item
                name="argumentId"
                label="Argument ID"
                style={{ display: 'none' }}
            >
                <InputNumber style={{ width: '100%' }} />
            </Form.Item>
            <Form.Item
                name="argumentVersion"
                label="Argument Version"
                style={{ display: 'none' }}
            >
                <InputNumber min={1} style={{ width: '100%' }} />
            </Form.Item>

            <Form.Item
                name="type"
                label="Type"
            >
                <Select onChange={type => setSuggestionType(type)}>
                    <Option value="REVISION">REVISION</Option>
                    <Option value="ADDITION">ADDITION</Option>
                </Select>
            </Form.Item>

            {suggestionType === "REVISION" &&
                <Form.Item
                    name="section"
                    label="Section"
                    rules={[{ required: true, message: 'Please input the section!' }]}

                >
                    {renderSelectSection()}
                </Form.Item>
            }


            <Form.Item
                name="text"
                label="New value"
                rules={[{ required: true, message: 'Please input the text for your suggestion!' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="comment"
                label="Comment"
                rules={[{ required: false }]}
            >
                <TextArea rows={2} />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
};

export default SuggestionForm;
