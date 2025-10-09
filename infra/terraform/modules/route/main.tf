
resource "aws_route_table" "public" {
  vpc_id = var.vpc_id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = var.internet_gateway_id
  }

  tags = {
    Name = "${var.env}-public"
  }
}

resource "aws_route_table" "private" {
  vpc_id = var.vpc_id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = var.nat_gateway_id
  }

  tags = {
    Name = "${var.env}-private"
  }
}

resource "aws_route_table_association" "private" {
  for_each       = toset(var.private_subnet_ids)
  subnet_id      = each.value
  route_table_id = aws_route_table.private.id
}

resource "aws_route_table_association" "public" {
  for_each       = toset(var.public_subnet_ids)
  subnet_id      = each.value
  route_table_id = aws_route_table.public.id
}
