import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {
  Row,
  Col,
  Card,
  Badge,
  Form,
  Input,
  Button,
  Table,
  Avatar,
  Select,
  DatePicker,
  Pagination,
  InputNumber,
  Tabs,
  Modal,
} from 'antd';

import moment from 'moment';
import styles from './${tablePathName}List.less';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {getUserInfo} from "@/utils/userInfo";
import { haveAuthority } from '@/utils/authority';

const { RangePicker } = DatePicker;
const EditableContext = React.createContext();
const FormItem = Form.Item;
const EditableFormRow = Form.create()(({ form, index, ...props }) => (
  <EditableContext.Provider value={form}>
    <tr {...props} />
  </EditableContext.Provider>
));

// 弹窗增加配置项
const CreateForm = Form.create()(prop => {
  const { modalVisible, form, handleAdd, hideAddModal } = prop;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;

      form.resetFields();
      handleAdd(fieldsValue);
    });
  };

  return (
    <Modal
      destroyOnClose
      title="新增"
      visible={modalVisible}
      onOk={okHandle}
      maskClosable={false}
      onCancel={() => hideAddModal()}
    >
      <#list insertFields! as addField>
      <FormItem labelCol={{ span: 6 }} wrapperCol={{ span: 16 }} label="${addField.fieldInfo.desc}" hasFeedback>
        {form.getFieldDecorator('${addField.fieldInfo.fieldMeta.codeName}', {
          rules: [{ required: true, message: '请输入${addField.fieldInfo.desc}！' }],
        })(<#if addField.fieldInfo.timeFlag == 1>
          <DatePicker
            style={{ width: '100%' }}
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            placeholder="Select Time"
          /><#elseif addField.fieldInfo.enumFlag==1 ><#list enumFields! as enumField><#if enumField.fieldName == addField.fieldInfo.dbName>
          <Select style={{ width: '100%' }}>
          <#list enumField.metaList! as meta>
            <Select.Option value="${meta.name}">${meta.desc}</Select.Option>
          </#list>
          </Select></#if></#list><#else >
          <Input placeholder="请输入${addField.fieldInfo.desc}" /></#if>)}
      </FormItem>
      </#list>
    </Modal>
  );
});

const EditForm = Form.create()(props => {
  const { modalVisible, form, handleEdit, hideEditModal, item } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) {
        return;
      }
      form.resetFields();
      handleEdit(fieldsValue);
    });
  };

  return (
    <Modal
      destroyOnClose
      title="编辑"
      visible={modalVisible}
      onOk={okHandle}
      maskClosable={false}
      onCancel={() => hideEditModal()}
    >
    <#list updateFields! as updateField>
      <FormItem labelCol={{ span: 6 }} wrapperCol={{ span: 16 }} label="${updateField.fieldInfo.desc}">
        {form.getFieldDecorator('${updateField.fieldInfo.fieldMeta.codeName}', {
          initialValue: <#if updateField.fieldInfo.timeFlag == 1>moment(item.${updateField.fieldInfo.fieldMeta.codeName})<#else >item.${updateField.fieldInfo.fieldMeta.codeName}</#if>,
          rules: [{ required: true, message: '请输入${updateField.fieldInfo.desc}！' }],
        })(<#if updateField.fieldInfo.timeFlag == 1>
          <DatePicker
            style={{ width: '100%' }}
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            placeholder="Select Time"
            <#if updateField.canEdit == 0>disabled</#if>
          /><#elseif updateField.fieldInfo.enumFlag==1 ><#list enumFields! as enumField><#if enumField.fieldName == updateField.fieldInfo.fieldMeta.dbName>
          <Select style={{ width: '100%' }}<#if updateField.canEdit == 0>disabled</#if>>
          <#list enumField.metaList! as meta>
            <Select.Option value="${meta.name}">${meta.desc}</Select.Option>
          </#list>
          </Select></#if></#list><#else >
          <Input placeholder="请输入${updateField.fieldInfo.desc}" <#if updateField.canEdit == 0>disabled</#if> /></#if>
        )}
      </FormItem>
    </#list>
    </Modal>
  );
});

// 可编辑的列中的元素
class EditableCell extends PureComponent {
  getInput = () => {
    const { inputType } = this.props;
    if (inputType === 'number') {
      return <InputNumber />;
    }
    return <Input />;
  };

  render() {
    const { editing, dataIndex, title, inputType, record, index, ...restProps } = this.props;
    return (
      <EditableContext.Consumer>
        {form => {
          const { getFieldDecorator } = form;
          return (
            <td {...restProps}>
              {editing ? (
                <FormItem style={{ margin: 0 }}>
                  {getFieldDecorator(dataIndex, {
                      rules: [
                          {
                              required: true,
                              message: `请输入 ${r"${title}"}!`,
                          },
                      ],
                      initialValue: record[dataIndex],
                  })(this.getInput())}
                </FormItem>
              ) : (
              restProps.children
              )}
            </td>
          );
          }}
      </EditableContext.Consumer>
    );
  }
}

/* eslint react/no-multi-comp:0 */
@connect(({ ${tablePathNameLower}Model, authModel, loading}) => ({
  ${tablePathNameLower}Model,
  authModel,
  loading: loading.models.${tablePathNameLower}Model,
}))
// @Form.create() 是一个注解，就简化了xxx = Form.create(xxx);export xxx
@Form.create()
class ${tablePathName}List extends PureComponent {
  state = {
    addModalVisible: false,
    editModalVisible: false,
    item: {},
  };

  columns = [
    <#list tableShowFields! as f>
    {
      name: '${f.fieldMeta.codeName}',
      title: '${f.desc}',
      dataIndex: '${f.fieldMeta.codeName}',
      width: '20%',
      <#if f.timeFlag == 1>
      render: (text, record) => (
        // eslint-disable-next-line radix
        <span>{moment(record.${f.fieldMeta.codeName}).format('YYYY-MM-DD HH:mm:ss')}</span>
      ),
      <#elseif f.picFlag == 1>
      render: (text, record) => <Avatar shape="square" src={record.${f.fieldMeta.codeName}} />,
      </#if>
    },
    </#list>
    {
      name: 'edit',
      title: '编辑',
      dataIndex: 'edit',
      width: '10%',
      render: (text, record) => (
        <span>
          <Button type="primary" icon="edit" onClick={() => this.showEditModal(record)} />
        </span>
      ),
    },
    {
      name: 'delete',
      title: '删除',
      dataIndex: 'delete',
      editable: false,
      width: '5%',
      render: (text, row) => (
        <span>
          <Button type="danger" icon="delete" onClick={() => this.showDeleteConfirm(row)} />
        </span>
      ),
    },
  ];

  // 界面初始化函数
  componentDidMount() {
    // 获取权限
    this.getAuth();

    // 获取页面的总个数
    this.getPageData(1);
  }

  // 刷新用户界面的权限
  getAuth() {
    const {dispatch} = this.props;
    dispatch({
      type: 'authModel/getAuthOfUser',
    });
  }

  getPageData(pageNo, searchParamInput) {
    const { dispatch } = this.props;
    const {
        ${tablePathNameLower}Model: { searchParam, pager },
    } = this.props;

    this.setTableLoading();
    let param = searchParam;
    if (searchParamInput !== undefined) {
      param = searchParamInput;
    }

    let pagerFinal = pager;
    if (pageNo !== undefined) {
      pagerFinal = {
        ...pager,
        pageNo,
      };
    }

    console.log("pager param");
    console.log(JSON.stringify(pagerFinal));
    console.log(JSON.stringify(param));

    // 获取页面的总个数
    dispatch({
      type: 'cityModel/getPage',
      payload: {
        pager: pagerFinal,
        searchParam: param,
      },
    });
  }

  expandedRowRender = record => (
    <div>
     <#list expandFields! as expandField>
      <Row>
       <#list expandField! as expand>
        <Col span={6}>
          <Badge status="success" text="${expand.desc}：" />
          <#if expand.timeFlag == 1>{/* eslint-disable-next-line radix */}
          <span>{moment(record.${expand.fieldMeta.codeName}).format('YYYY-MM-DD HH:mm:ss')}</span><#elseif expand.picFlag == 1><Avatar shape="square" src={record.${expand.fieldMeta.codeName}} />><#else ><span>{record.${expand.fieldMeta.codeName}}</span></#if>
        </Col>
       </#list>
      </Row>
      <br />
     </#list>
    </div>
  );

  showDeleteConfirm = row => {
    const { dispatch } = this.props;
    // console.log('点击');
    // console.log(JSON.stringify(row));
    const showLoading = ()=>this.setTableLoading();
    Modal.confirm({
      title: '确定要删除这条配置',
      okText: '确定删除',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        showLoading();
        console.log('OK');
        dispatch({
          type: '${tablePathNameLower}Model/delete',
          payload: {
            id:row.id,
          },
        });
      },
      onCancel() {
        console.log('Cancel');
      },
    });
  };

  showAddModal = () => {
    this.setState({
      addModalVisible: true,
    });
  };

  hideAddModal = () => {
    this.setState({
      addModalVisible: false,
    });
  };

  showEditModal = record => {
    console.log('点击编辑');
    this.setState({
      item: record,
      editModalVisible: true,
    });
  };

  hideEditModal = () => {
    this.setState({
      editModalVisible: false,
    });
  };

  // 设置表格加载
  setTableLoading = () => {
    const { dispatch } = this.props;
    dispatch({
      type: '${tablePathNameLower}Model/setTableLoading',
    });
  };

  // 添加
  handleAdd = fields => {
    const { dispatch } = this.props;
    const userInfo = getUserInfo();
    let userName = "";
    if(userInfo !== null) {
      userName = userInfo.displayName;
    }

    this.setTableLoading();

    // 将中间添加的脚本放进去
    const params = {
      ...fields,
      createUserName: userName,
    };

    dispatch({
      type: '${tablePathNameLower}Model/add',
      payload: params,
    });

    this.hideAddModal();
  };

  // 判断对象1是否包含对象2的所有属性
  contain = (object1, object2) => {
    let index = 0;
    const keys = Object.keys(object2);
    for (let i = 0; i < keys.length; i += 1) {
      const name = keys[i];
      if (object1[name] && object2[name] === object1[name]) {
        index += 1;
      }
    }
    return index === Object.keys(object2).length;
  };

  handleEdit = fields => {
    const { dispatch } = this.props;
    const { item } = this.state;
    const userInfo = getUserInfo();
    let userName = "";
    if(userInfo !== null) {
      userName = userInfo.displayName;
    }

    console.log('编辑修改');
    console.log(JSON.stringify(fields));
    console.log(JSON.stringify(item));

    // 判断是否有修改，如果没有修改，则不向后端发起更新
    if (!this.contain(item, fields)) {
      this.setTableLoading();
      console.log('有变化需要修改');
      const params = {
        ...Object.assign(item, fields),
        updateUserName: userName,
      };

      console.log(JSON.stringify(params));
      dispatch({
        type: '${tablePathNameLower}Model/update',
        payload: params,
      });
    }

    this.hideEditModal();
  };

  handleSearch = e => {
    e.preventDefault();

    const { form } = this.props;

    console.log('启动查询');
    this.setTableLoading();

    form.validateFields((err, fieldsValue) => {
      if (err) return;
      for(let key in fieldsValue) {
        if(fieldsValue[key] === '') {
          delete fieldsValue[key]
        }
      };
      this.getPageData(1, fieldsValue);
    });
  };

  // 加载搜索输入框和搜索按钮
  renderSearchForm = () => {
    const {
      form: { getFieldDecorator },
    } = this.props;

    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <#list searchFields! as searchField>
          <Col lg={5}>
            <FormItem label="${searchField.desc}">
              {getFieldDecorator('${searchField.fieldMeta.codeName}')(
                <#if searchField.timeFlag == 1>
                <RangePicker
                  showTime={{
                     hideDisabledOptions: true,
                     defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('11:59:59', 'HH:mm:ss')],
                  }}
                  format="YYYY-MM-DD HH:mm:ss"
                />
                <#elseif searchField.enumFlag==1 >
                <#list enumFields! as enumField>
                <#if enumField.fieldName == searchField.fieldMeta.codeName>
                <Select allowClear>
                  <#list enumField.metaList! as meta>
                  <Select.Option value="${meta.name}">${meta.desc}</Select.Option>
                  </#list>
                </Select>
                </#if>
                </#list>
                <#else >
                <Input placeholder="请输入" />
                </#if >
               )}
            </FormItem>
          </Col>
          </#list>
          <Col md={2} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
            </span>
          </Col>
          <Col md={2} sm={24}>
            <Button icon="plus" type="primary" onClick={this.showAddModal}>
              新建
            </Button>
          </Col>
        </Row>
      </Form>
    );
  };

  onChange = page => {
    console.log('页面索引修改');

    this.getPageData(page);
  };

  render() {
    const {
       ${tablePathNameLower}Model: { selectState, groupAllCodeList },
    } = this.props;

    // 替换表Table的组件
    const components = {
      body: {
        row: EditableFormRow,
        cell: EditableCell,
      },
    };

    const { addModalVisible, editModalVisible, item } = this.state;
    const parentAddMethods = {
      selectState,
      groupAllCodeList,
      handleAdd: this.handleAdd,
      hideAddModal: this.hideAddModal,
    };
    const parentEditMethods = {
      item,
      handleEdit: this.handleEdit,
      hideEditModal: this.hideEditModal,
    };

    const {
       ${tablePathNameLower}Model: { totalNumber, pager, tableList, tableLoading },
    } = this.props;

    const tableInfo = () => (
      <Card bordered={false}>
        <div className={styles.tableList}>
          <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
          <Table
            size="middle"
            rowKey={record => record.id}
            components={components}
            dataSource={tableList}
            columns={this.columns}
            loading={tableLoading}
            pagination={false}
          <#if expandExist == 1>
            expandedRowRender={this.expandedRowRender}
          </#if>
          />
          <br />
          <Pagination
            showQuickJumper
            onChange={this.onChange}
            defaultCurrent={1}
            total={totalNumber}
            current={pager.pageNo}
            defaultPageSize={pager.pageSize}
          />
        </div>
      </Card>
    );

    return (
      <PageHeaderWrapper>
        {tableInfo()}
        <CreateForm {...parentAddMethods} modalVisible={addModalVisible} />
        <EditForm {...parentEditMethods} modalVisible={editModalVisible} />
      </PageHeaderWrapper>
    );
  }
}

export default ${tablePathName}List;
