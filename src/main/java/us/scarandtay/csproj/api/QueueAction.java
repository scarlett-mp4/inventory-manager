package us.scarandtay.csproj.api;

//          _________   ________________________  ____
//         \_   ___ \ /   _____/\_____  \   _  \/_   |
//         /    \  \/ \_____  \  /  ____/  /_\  \|   |
//         \     \____/        \/       \  \_/   \   |
//          \______  /_______  /\_______ \_____  /___|
//                 \/        \/         \/     \/
//                      Inventory Manager
//        Written by: Scarlett Kadan & Taylor Washington
public interface QueueAction {
    // Will queue the desired method. This will fight
    // against concurrent file modification.
    void queue();
}