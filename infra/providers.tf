
provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      Project     = "task-management"
      Environment = var.environment
      ManagedBy   = "Terraform"
    }
  }
}

data "aws_availability_zones" "available" {
  state = "available"
}
