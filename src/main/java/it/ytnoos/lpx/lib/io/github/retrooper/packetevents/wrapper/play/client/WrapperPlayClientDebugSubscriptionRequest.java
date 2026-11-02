package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.DebugSubscription;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.DebugSubscriptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
public class WrapperPlayClientDebugSubscriptionRequest
        extends PacketWrapper<WrapperPlayClientDebugSubscriptionRequest> {

    private Set<DebugSubscription<?>> subscriptions;

    public WrapperPlayClientDebugSubscriptionRequest(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientDebugSubscriptionRequest(Set<DebugSubscription<?>> subscriptions) {
        super(PacketType.Play.Client.DEBUG_SUBSCRIPTION_REQUEST);
        this.subscriptions = subscriptions;
    }

    @Override
    public void read() {
        this.subscriptions =
                this.readSet(result -> result.readMappedEntity(DebugSubscriptions.getRegistry()));
    }

    @Override
    public void write() {
        this.writeSet(this.subscriptions, PacketWrapper::writeMappedEntity);
    }

    public void copy(
            WrapperPlayClientDebugSubscriptionRequest wrapperPlayClientDebugSubscriptionRequest) {
        this.subscriptions = wrapperPlayClientDebugSubscriptionRequest.subscriptions;
    }

    public Set<DebugSubscription<?>> getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Set<DebugSubscription<?>> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
