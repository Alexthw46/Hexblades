Stream.of(
Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
Block.makeCuboidShape(2, 1, 3, 3, 2, 14),
Block.makeCuboidShape(13, 1, 2, 14, 2, 13),
Block.makeCuboidShape(2, 1, 2, 13, 2, 3),
Block.makeCuboidShape(3, 1, 13, 14, 2, 14)
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});