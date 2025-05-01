-- Вставляем теги (10 популярных тегов)
INSERT INTO tags(id, name) VALUES
('10000000-0000-0000-0000-000000000001', 'Technology'),
('10000000-0000-0000-0000-000000000002', 'Travel'),
('10000000-0000-0000-0000-000000000003', 'Food'),
('10000000-0000-0000-0000-000000000004', 'Fitness'),
('10000000-0000-0000-0000-000000000005', 'Music'),
('10000000-0000-0000-0000-000000000006', 'Photography'),
('10000000-0000-0000-0000-000000000007', 'Art'),
('10000000-0000-0000-0000-000000000008', 'Business'),
('10000000-0000-0000-0000-000000000009', 'Science'),
('10000000-0000-0000-0000-000000000010', 'Gaming');

-- Пост 1
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000001', '1 The Future of AI', 'Artificial intelligence is transforming industries...', 'How AI is changing the world', 'tech-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000001', 42);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001'),
('20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000009');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', 'Great article about AI!'),
('30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', 'I work in this field and can confirm these trends');

-- Пост 2
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000002', '2 My Travel to Japan', 'Exploring Tokyo and Kyoto was an amazing experience...', 'Japanese culture insights', 'travel-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000002', 87);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000002'),
('20000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000007');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000003', '20000000-0000-0000-0000-000000000002', 'I was there last year, amazing places!'),
('30000000-0000-0000-0000-000000000004', '20000000-0000-0000-0000-000000000002', 'What was your favorite spot?'),
('30000000-0000-0000-0000-000000000005', '20000000-0000-0000-0000-000000000002', 'Great photos!');

-- Пост 3-30 (шаблон для остальных постов)
-- Каждый следующий пост будет использовать аналогичную структуру с увеличением ID
-- Например, для поста 3:
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000003', '3 --10 Tips for Healthy Eating', 'Balanced nutrition is key to maintaining good health...', 'Nutrition advice', 'food-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000003', 35);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003'),
('20000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000004');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000006', '20000000-0000-0000-0000-000000000003', 'Very useful tips, thanks!'),
('30000000-0000-0000-0000-000000000007', '20000000-0000-0000-0000-000000000003', 'I tried #3 and it works great');

-- Пост 4
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000004', '4 Guitar Techniques for Beginners', 'Learning proper technique early will help you progress faster...', 'Basic guitar tips', 'music-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000004', 28);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000005');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000008', '20000000-0000-0000-0000-000000000004', 'Wish I had this when I started!');

-- Пост 5
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000005', '5 Street Photography Guide', 'Capturing authentic moments in urban environments requires...', 'Urban photography tips', 'photo-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000005', 64);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000005', '10000000-0000-0000-0000-000000000006'),
('20000000-0000-0000-0000-000000000005', '10000000-0000-0000-0000-000000000007');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000009', '20000000-0000-0000-0000-000000000005', 'What camera do you recommend?'),
('30000000-0000-0000-0000-000000000010', '20000000-0000-0000-0000-000000000005', 'Great composition examples!');

-- Пост 6
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000006', '6 Startup Funding Strategies', 'Raising capital is one of the biggest challenges for...', 'Business financing guide', 'business-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000006', 39);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000006', '10000000-0000-0000-0000-000000000008');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000011', '20000000-0000-0000-0000-000000000006', 'We bootstrapped our company successfully'),
('30000000-0000-0000-0000-000000000012', '20000000-0000-0000-0000-000000000006', 'VC funding isn''t for everyone');

-- Пост 7
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000007', '7 Quantum Computing Explained', 'Qubits and superposition might sound like science fiction...', 'Quantum physics basics', 'science-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000007', 73);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000007', '10000000-0000-0000-0000-000000000001'),
('20000000-0000-0000-0000-000000000007', '10000000-0000-0000-0000-000000000009');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000013', '20000000-0000-0000-0000-000000000007', 'When will this be commercially available?'),
('30000000-0000-0000-0000-000000000014', '20000000-0000-0000-0000-000000000007', 'IBM has some public quantum computers'),
('30000000-0000-0000-0000-000000000015', '20000000-0000-0000-0000-000000000007', 'Mind-blowing technology!');

-- Пост 8
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000008', '8 Game Development Diary', 'Creating my first indie game has been challenging but...', 'Dev log week 1', 'game-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000008', 51);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000008', '10000000-0000-0000-0000-000000000010'),
('20000000-0000-0000-0000-000000000008', '10000000-0000-0000-0000-000000000001');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000016', '20000000-0000-0000-0000-000000000008', 'What engine are you using?'),
('30000000-0000-0000-0000-000000000017', '20000000-0000-0000-0000-000000000008', 'Looking forward to updates!');

-- Пост 9
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000009', '9 Mediterranean Diet Benefits', 'The Mediterranean diet emphasizes fruits, vegetables, whole grains...', 'Healthy eating patterns', 'food-2.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000009', 56);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000009', '10000000-0000-0000-0000-000000000003'),
('20000000-0000-0000-0000-000000000009', '10000000-0000-0000-0000-000000000004');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000018', '20000000-0000-0000-0000-000000000009', 'Lost 10kg following this diet!'),
('30000000-0000-0000-0000-000000000019', '20000000-0000-0000-0000-000000000009', 'What about olive oil recommendations?');

-- Пост 10
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000010', '10 Yoga for Stress Relief', 'These 5 yoga poses can help reduce anxiety and improve...', 'Mind-body connection', 'fitness-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000010', 72);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000010', '10000000-0000-0000-0000-000000000004');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000020', '20000000-0000-0000-0000-000000000010', 'Perfect for my morning routine'),
('30000000-0000-0000-0000-000000000021', '20000000-0000-0000-0000-000000000010', 'Helped me sleep better!');

-- Пост 11
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000011', '11 Blockchain Beyond Cryptocurrency', 'From supply chain to healthcare, blockchain technology has...', 'Real-world blockchain apps', 'tech-2.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000011', 48);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000011', '10000000-0000-0000-0000-000000000001'),
('20000000-0000-0000-0000-000000000011', '10000000-0000-0000-0000-000000000008');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000022', '20000000-0000-0000-0000-000000000011', 'What about energy consumption issues?'),
('30000000-0000-0000-0000-000000000023', '20000000-0000-0000-0000-000000000011', 'Great non-crypto examples');

-- Пост 12
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000012', '12 Best Travel Cameras 2023', 'After testing 15 cameras, here are my top picks for...', 'Travel photography gear', 'photo-2.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000012', 89);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000012', '10000000-0000-0000-0000-000000000002'),
('20000000-0000-0000-0000-000000000012', '10000000-0000-0000-0000-000000000006');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000024', '20000000-0000-0000-0000-000000000012', 'Would you recommend mirrorless for beginners?'),
('30000000-0000-0000-0000-000000000025', '20000000-0000-0000-0000-000000000012', 'Missing the new Sony model'),
('30000000-0000-0000-0000-000000000026', '20000000-0000-0000-0000-000000000012', 'Great comparison!');

-- Пост 13
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000013', '13 Machine Learning Applications', 'How ML is transforming industries from finance to...', 'Practical AI uses', 'tech-3.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000013', 63);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000013', '10000000-0000-0000-0000-000000000001'),
('20000000-0000-0000-0000-000000000013', '10000000-0000-0000-0000-000000000009');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000027', '20000000-0000-0000-0000-000000000013', 'What about ethical considerations?'),
('30000000-0000-0000-0000-000000000028', '20000000-0000-0000-0000-000000000013', 'We use similar models in healthcare');

-- Пост 14
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000014', '14 Modern Art Trends', 'Abstract expressionism meets digital media in these...', 'Contemporary art review', 'art-1.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000014', 37);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000014', '10000000-0000-0000-0000-000000000007');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000029', '20000000-0000-0000-0000-000000000014', 'The NFT connection is fascinating'),
('30000000-0000-0000-0000-000000000030', '20000000-0000-0000-0000-000000000014', 'Not sure I understand all of it');

-- Пост 15
INSERT INTO posts(id, title, text, text_preview, image_path) VALUES
('20000000-0000-0000-0000-000000000015', '15 Home Workout Routine', 'No gym? No problem. This 30-minute routine requires only...', 'Equipment-free exercises', 'fitness-2.jpg');

INSERT INTO likes(post_id, likes_count) VALUES
('20000000-0000-0000-0000-000000000015', 94);

INSERT INTO tags_to_post(post_id, tag_id) VALUES
('20000000-0000-0000-0000-000000000015', '10000000-0000-0000-0000-000000000004');

INSERT INTO comments(id, post_id, content) VALUES
('30000000-0000-0000-0000-000000000031', '20000000-0000-0000-0000-000000000015', 'Perfect for small apartments!'),
('30000000-0000-0000-0000-000000000032', '20000000-0000-0000-0000-000000000015', 'How often should I do this?'),
('30000000-0000-0000-0000-000000000033', '20000000-0000-0000-0000-000000000015', 'Saved for my quarantine routine');
