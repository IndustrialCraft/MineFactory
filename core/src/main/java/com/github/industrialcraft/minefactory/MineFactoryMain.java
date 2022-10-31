package com.github.industrialcraft.minefactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.minefactory.controller.IController;
import com.github.industrialcraft.minefactory.controller.KeyboardController;
import com.github.industrialcraft.minefactory.inventory.InventoryRenderer;
import com.github.industrialcraft.minefactory.inventory.ItemRegistry;
import com.github.industrialcraft.minefactory.util.HitDetection;
import com.github.industrialcraft.minefactory.util.SelectorRenderer;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.blocks.BlockRegistry;
import com.github.industrialcraft.minefactory.world.entities.ItemEntityDef;
import com.github.industrialcraft.minefactory.world.tiles.TileRegistry;
import com.github.industrialcraft.minefactory.world.World;
import com.github.industrialcraft.minefactory.world.entities.EntityRegistry;
import com.github.industrialcraft.minefactory.world.entities.PlayerEntityDef;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;
import com.github.industrialcraft.minefactory.worldgen.IChunkSupplier;
import com.github.industrialcraft.minefactory.worldgen.RandomChunkSupplier;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MineFactoryMain extends ApplicationAdapter {
	public ItemRegistry itemRegistry;
	public TileRegistry tileRegistry;
	public EntityRegistry entityRegistry;
	public BlockRegistry blockRegistry;
	public World world;
	public IChunkSupplier chunkSupplier;
	public SpriteBatch batch;
	public WorldEntity player;
	public OrthographicCamera camera;
	public OrthographicCamera uiCamera;
	public IController controller;
	public SelectorRenderer selectorRenderer;
	public InventoryRenderer inventoryRenderer;
	public BitmapFont font;
	@Override
	public void create() {
		this.font = new BitmapFont();
		this.controller = new KeyboardController();
		Gdx.input.setInputProcessor(controller);
		this.itemRegistry = new ItemRegistry();
		this.itemRegistry.loadFromFolder(Gdx.files.internal("assets/items"));
		this.tileRegistry = new TileRegistry();
		this.tileRegistry.loadFromFolder(Gdx.files.internal("assets/tiles"));
		this.entityRegistry = new EntityRegistry();
		this.entityRegistry.loadFromFolder(Gdx.files.internal("assets/entities"));
		this.entityRegistry.register(PlayerEntityDef.PLAYER_ENTITY_DEF);
		this.entityRegistry.register(ItemEntityDef.ITEM_ENTITY_DEF);
		this.blockRegistry = new BlockRegistry();
		this.blockRegistry.loadFromFolder(Gdx.files.internal("assets/blocks"));
		this.chunkSupplier = new RandomChunkSupplier(20, blockRegistry.get(Identifier.of("mf", "rock")), itemRegistry.get(Identifier.of("mf", "stick")), tileRegistry.get(Identifier.of("mf","grass")), tileRegistry.get(Identifier.of("mf","sand")), tileRegistry.get(Identifier.of("mf","water")));
		this.world = new World(chunkSupplier);
		this.batch = new SpriteBatch();
		this.player = new WorldEntity(PlayerEntityDef.PLAYER_ENTITY_DEF, new Position(0, 0), world);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.uiCamera = new OrthographicCamera(1000, 750);
		this.selectorRenderer = new SelectorRenderer(new TextureRegion(new Texture("assets/textures/selector.png")), batch);
		this.inventoryRenderer = new InventoryRenderer(batch, new TextureRegion(new Texture("assets/textures/slot.png")), new TextureRegion(new Texture("assets/textures/slot-selected.png")), font);
	}

	@Override
	public void render() {
		world.tick(player);
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.camera.position.x = player.getPosition().x * Position.PIXELS_PER_TILE;
		this.camera.position.y = player.getPosition().y * Position.PIXELS_PER_TILE;
		this.camera.zoom = 0.5f;
		this.camera.update();
		this.batch.setProjectionMatrix(camera.combined);
		this.batch.begin();
		world.render(batch);
		HitDetection.Hit hit = HitDetection.tryHit(controller.getSelectedTile(this), world);
		selectorRenderer.render(hit, Color.RED);
		if(controller.isADown() && hit instanceof HitDetection.EntityHit entityHit){
			entityHit.entity.entityDef.interact(entityHit.entity, player);
		}
		controller.render();
		this.batch.end();
		uiCamera.update();
		this.batch.setProjectionMatrix(uiCamera.combined);
		this.batch.begin();
		inventoryRenderer.render(player.inventory, (int) (-4.5*InventoryRenderer.SLOT_SIZE), -400+InventoryRenderer.SLOT_SIZE, 9, player.getHandSlot());
		this.batch.end();
		player.teleport(player.getPosition().add(controller.getHorizontal()*5*Gdx.graphics.getDeltaTime(), controller.getVertical()*5*Gdx.graphics.getDeltaTime()));
		player.setHandSlot(controller.getSlot(player.getHandSlot()));
	}

	@Override
	public void resize(int width, int height) {
		this.camera = new OrthographicCamera(width, height);
	}

	@Override
	public void dispose() {
		this.batch.dispose();
	}
}