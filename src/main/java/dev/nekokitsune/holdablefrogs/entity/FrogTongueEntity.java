package dev.nekokitsune.holdablefrogs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FrogTongueEntity extends ProjectileEntity {
    private static final TrackedData<Integer> OWNER_ID =
            DataTracker.registerData(FrogTongueEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int ownerId = -1;
    private int life;
    private final FrogVariant frogVariant;

    public FrogTongueEntity(EntityType<? extends ProjectileEntity> type, World world, FrogVariant variant) {
        super(type, world);
        this.frogVariant = variant;
    }

    public FrogTongueEntity(EntityType<FrogTongueEntity> frogTongueEntityEntityType, World world) {
        super(frogTongueEntityEntityType, world);
        this.frogVariant = FrogVariant.TEMPERATE;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(OWNER_ID, -1);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            this.ownerId = this.dataTracker.get(OWNER_ID);
        }

        if (!this.getWorld().isClient) {
            life++;
            if (life > 20) {
                this.discard();
                return;
            }

            Vec3d currentPos = this.getPos();
            Vec3d nextPos = currentPos.add(this.getVelocity());

            Box box = this.getBoundingBox().stretch(this.getVelocity()).expand(0.5);

            Predicate<Entity> predicate = this::canHitEntity;

            EntityHitResult entityHit = ProjectileUtil.getEntityCollision(this.getWorld(), this, currentPos, nextPos, box, predicate);
            if (entityHit != null) {
                this.onEntityHit(entityHit);
                return;
            }

            HitResult hit = ProjectileUtil.getCollision(this, predicate);
            if (hit.getType() != HitResult.Type.MISS) {
                this.onCollision(hit);
                return;
            }
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult) hitResult);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult) hitResult);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        Entity target = hit.getEntity();
        Entity owner = this.getOwner();

        if (owner instanceof PlayerEntity player) {
            Vec3d direction = player.getPos().add(0, player.getStandingEyeHeight(), 0)
                    .subtract(target.getPos())
                    .normalize()
                    .multiply(1.5);

            target.addVelocity(direction.x, direction.y, direction.z);
            target.velocityModified = true;

            if (target.getType() == EntityType.MAGMA_CUBE) {
                World world = this.getWorld();
                BlockPos playerPos = owner.getBlockPos();
                target.discard();

                if (frogVariant == FrogVariant.TEMPERATE) {
                    ItemStack froglight = new ItemStack(Items.OCHRE_FROGLIGHT);
                    ItemEntity froglightEntity = new ItemEntity(world,
                            playerPos.getX(), playerPos.getY(), playerPos.getZ(),
                            froglight);
                    world.spawnEntity(froglightEntity);
                } else if (frogVariant == FrogVariant.COLD) {
                    ItemStack froglight = new ItemStack(Items.VERDANT_FROGLIGHT);
                    ItemEntity froglightEntity = new ItemEntity(world,
                            playerPos.getX(), playerPos.getY(), playerPos.getZ(),
                            froglight);
                    world.spawnEntity(froglightEntity);
                } else if (frogVariant == FrogVariant.WARM) {
                    ItemStack froglight = new ItemStack(Items.PEARLESCENT_FROGLIGHT);
                    ItemEntity froglightEntity = new ItemEntity(world,
                            playerPos.getX(), playerPos.getY(), playerPos.getZ(),
                            froglight);
                    world.spawnEntity(froglightEntity);
                }
            }

            player.getWorld().playSound(null, owner.getBlockPos(),
                    SoundEvents.ENTITY_FROG_EAT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult hit) {
        this.discard();
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    protected boolean canHitEntity(Entity entity) {
        return entity.isAlive() && !entity.isSpectator() && entity != this.getOwner();
    }

    @Override
    public void setOwner(Entity owner) {
        super.setOwner(owner);
        this.ownerId = owner != null ? owner.getId() : -1;
        this.dataTracker.set(OWNER_ID, this.ownerId);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("OwnerId", this.ownerId);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.ownerId = nbt.getInt("OwnerId");
    }

    @Nullable
    @Override
    public Entity getOwner() {
        Entity owner = super.getOwner();
        if (owner == null && this.ownerId != -1) {
            owner = this.getWorld().getEntityById(this.ownerId);
            if (owner != null) {
                super.setOwner(owner);
            }
        }
        return owner;
    }
}
