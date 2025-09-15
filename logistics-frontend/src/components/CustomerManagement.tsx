import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  Paper,
  TextField,
  Typography,
  Alert,
  Snackbar,
} from '@mui/material';
import {
  Add as AddIcon,
  Search as SearchIcon,
} from '@mui/icons-material';
import { DataGrid, GridColDef, GridActionsCellItem } from '@mui/x-data-grid';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { customerApi, Customer } from '../services/api';

const CustomerManagement: React.FC = () => {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editingCustomer, setEditingCustomer] = useState<Customer | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });

  const [formData, setFormData] = useState({
    customerId: '',
    customerName: '',
    address: '',
    phone: '',
  });

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      setLoading(true);
      const response = await customerApi.getAll();
      setCustomers(response.data);
    } catch (error) {
      console.error('Failed to fetch customers:', error);
      showSnackbar('顧客データの取得に失敗しました', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      fetchCustomers();
      return;
    }

    try {
      setLoading(true);
      const response = await customerApi.search(searchTerm);
      setCustomers(response.data);
    } catch (error) {
      console.error('Failed to search customers:', error);
      showSnackbar('顧客検索に失敗しました', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleOpenDialog = (customer?: Customer) => {
    if (customer) {
      setEditingCustomer(customer);
      setFormData({
        customerId: customer.customerId,
        customerName: customer.customerName,
        address: customer.address,
        phone: customer.phone,
      });
    } else {
      setEditingCustomer(null);
      setFormData({
        customerId: '',
        customerName: '',
        address: '',
        phone: '',
      });
    }
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
    setEditingCustomer(null);
    setFormData({
      customerId: '',
      customerName: '',
      address: '',
      phone: '',
    });
  };

  const handleSubmit = async () => {
    try {
      if (editingCustomer) {
        await customerApi.update(editingCustomer.customerId, formData);
        showSnackbar('顧客を更新しました', 'success');
      } else {
        await customerApi.create(formData);
        showSnackbar('顧客を作成しました', 'success');
      }
      handleCloseDialog();
      fetchCustomers();
    } catch (error) {
      console.error('Failed to save customer:', error);
      showSnackbar('顧客の保存に失敗しました', 'error');
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm('この顧客を削除しますか？')) {
      try {
        await customerApi.delete(id);
        showSnackbar('顧客を削除しました', 'success');
        fetchCustomers();
      } catch (error) {
        console.error('Failed to delete customer:', error);
        showSnackbar('顧客の削除に失敗しました', 'error');
      }
    }
  };

  const showSnackbar = (message: string, severity: 'success' | 'error') => {
    setSnackbar({ open: true, message, severity });
  };

  const columns: GridColDef[] = [
    { field: 'customerId', headerName: '顧客ID', width: 120 },
    { field: 'customerName', headerName: '顧客名', width: 200 },
    { field: 'address', headerName: '住所', width: 300 },
    { field: 'phone', headerName: '電話番号', width: 150 },
    {
      field: 'createdAt',
      headerName: '作成日時',
      width: 180,
      valueFormatter: (params: any) => new Date(params.value).toLocaleString('ja-JP'),
    },
    {
      field: 'actions',
      type: 'actions',
      headerName: '操作',
      width: 120,
      getActions: (params) => [
        <GridActionsCellItem
          icon={<EditIcon />}
          label="編集"
          onClick={() => handleOpenDialog(params.row)}
        />,
        <GridActionsCellItem
          icon={<DeleteIcon />}
          label="削除"
          onClick={() => handleDelete(params.row.customerId)}
        />,
      ],
    },
  ];

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">顧客管理</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpenDialog()}
        >
          新規顧客
        </Button>
      </Box>

      <Paper sx={{ mb: 3, p: 2 }}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="顧客名で検索"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            />
          </Grid>
          <Grid item xs={12} sm={3}>
            <Button
              fullWidth
              variant="outlined"
              startIcon={<SearchIcon />}
              onClick={handleSearch}
            >
              検索
            </Button>
          </Grid>
          <Grid item xs={12} sm={3}>
            <Button
              fullWidth
              variant="outlined"
              onClick={() => {
                setSearchTerm('');
                fetchCustomers();
              }}
            >
              リセット
            </Button>
          </Grid>
        </Grid>
      </Paper>

      <Paper sx={{ height: 600, width: '100%' }}>
        <DataGrid
          rows={customers}
          columns={columns}
          loading={loading}
          pageSizeOptions={[5, 10, 25, 50]}
          initialState={{
            pagination: { paginationModel: { pageSize: 10 } },
          }}
          pagination
          paginationMode="client"
          disableRowSelectionOnClick
          getRowId={(row) => row.customerId}
          sx={{
            '& .MuiDataGrid-footerContainer': {
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
            },
          }}
        />
      </Paper>

      <Dialog open={open} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingCustomer ? '顧客編集' : '新規顧客'}
        </DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="顧客ID"
                value={formData.customerId}
                onChange={(e) => setFormData({ ...formData, customerId: e.target.value })}
                disabled={!!editingCustomer}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="顧客名"
                value={formData.customerName}
                onChange={(e) => setFormData({ ...formData, customerName: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="住所"
                value={formData.address}
                onChange={(e) => setFormData({ ...formData, address: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="電話番号"
                value={formData.phone}
                onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                required
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>キャンセル</Button>
          <Button onClick={handleSubmit} variant="contained">
            {editingCustomer ? '更新' : '作成'}
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default CustomerManagement;
