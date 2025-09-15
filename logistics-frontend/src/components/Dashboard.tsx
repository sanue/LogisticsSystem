import React, { useState, useEffect } from 'react';
import {
  Box,
  Card,
  CardContent,
  Grid,
  Typography,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
} from '@mui/material';
import {
  Inventory as InventoryIcon,
  People as PeopleIcon,
  LocationOn as LocationIcon,
  TrendingUp as TrendingUpIcon,
} from '@mui/icons-material';
import { productApi, customerApi, locationApi, Product, Customer, Location } from '../services/api';

const Dashboard: React.FC = () => {
  const [stats, setStats] = useState({
    totalProducts: 0,
    totalCustomers: 0,
    totalLocations: 0,
  });
  const [recentProducts, setRecentProducts] = useState<Product[]>([]);
  const [recentCustomers, setRecentCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [productsRes, customersRes, locationsRes] = await Promise.all([
          productApi.getWithPagination(0, 1000), // 获取大量数据用于统计
          customerApi.getAll(),
          locationApi.getAll(),
        ]);

        setStats({
          totalProducts: productsRes.data.totalElements,
          totalCustomers: customersRes.data.length,
          totalLocations: locationsRes.data.length,
        });

        setRecentProducts(productsRes.data.content.slice(0, 5));
        setRecentCustomers(customersRes.data.slice(0, 5));
      } catch (error) {
        console.error('Failed to fetch dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const StatCard: React.FC<{
    title: string;
    value: number | string;
    icon: React.ReactNode;
    color: string;
  }> = ({ title, value, icon, color }) => (
    <Card>
      <CardContent>
        <Box display="flex" alignItems="center">
          <Box
            sx={{
              backgroundColor: color,
              borderRadius: '50%',
              p: 1,
              mr: 2,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
            }}
          >
            {icon}
          </Box>
          <Box>
            <Typography color="textSecondary" gutterBottom variant="h6">
              {title}
            </Typography>
            <Typography variant="h4" component="div">
              {value}
            </Typography>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <Typography>データを読み込み中...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        ダッシュボード
      </Typography>
      
      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="商品数"
            value={stats.totalProducts}
            icon={<InventoryIcon sx={{ color: 'white' }} />}
            color="#1976d2"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="顧客数"
            value={stats.totalCustomers}
            icon={<PeopleIcon sx={{ color: 'white' }} />}
            color="#388e3c"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="位置数"
            value={stats.totalLocations}
            icon={<LocationIcon sx={{ color: 'white' }} />}
            color="#f57c00"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="システム状態"
            value="正常"
            icon={<TrendingUpIcon sx={{ color: 'white' }} />}
            color="#7b1fa2"
          />
        </Grid>
      </Grid>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper>
            <Box p={2}>
              <Typography variant="h6" gutterBottom>
                最近の商品
              </Typography>
              <TableContainer>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>商品ID</TableCell>
                      <TableCell>商品名</TableCell>
                      <TableCell>単位</TableCell>
                      <TableCell>安全在庫</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {recentProducts.map((product) => (
                      <TableRow key={product.productId}>
                        <TableCell>{product.productId}</TableCell>
                        <TableCell>{product.productName}</TableCell>
                        <TableCell>{product.unit}</TableCell>
                        <TableCell>
                          <Chip
                            label={product.safetyStock}
                            color={product.safetyStock > 10 ? 'success' : 'warning'}
                            size="small"
                          />
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Box>
          </Paper>
        </Grid>

        <Grid item xs={12} md={6}>
          <Paper>
            <Box p={2}>
              <Typography variant="h6" gutterBottom>
                最近の顧客
              </Typography>
              <TableContainer>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>顧客ID</TableCell>
                      <TableCell>顧客名</TableCell>
                      <TableCell>住所</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {recentCustomers.map((customer) => (
                      <TableRow key={customer.customerId}>
                        <TableCell>{customer.customerId}</TableCell>
                        <TableCell>{customer.customerName}</TableCell>
                        <TableCell>{customer.address}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;
